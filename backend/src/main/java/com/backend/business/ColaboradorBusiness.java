package com.backend.business;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.entity.ColaboradorEntity;
import com.backend.repository.ColaboradorRepository;

@Service
public class ColaboradorBusiness {

	@Autowired
	ColaboradorRepository colaboradorRepository;
	
	private static final String SECRET_KEY = "mySecretKey12345"; // Chave de criptografia (16 bytes)
    private static final String INIT_VECTOR = "encryptionIntVec"; // Vetor de inicialização (16 bytes)

	public ColaboradorEntity findById(Integer id) {
		return colaboradorRepository.findById(id).get();
	}

	public List<ColaboradorEntity> findAll() {
		return colaboradorRepository.findAll();
	}

	public ColaboradorEntity save(ColaboradorEntity colaboradorEntity) {

		String passwordCheckResult[] = checkPassword(colaboradorEntity.getSenha()).split(";");

		colaboradorEntity.setComplexidade(passwordCheckResult[0]);
		colaboradorEntity.setScore(passwordCheckResult[1]);
		
		colaboradorEntity.setSenha(encrypt(colaboradorEntity.getSenha()));

		return colaboradorRepository.save(colaboradorEntity);
	}

	public static String checkPassword(String pwd) {
		// Your Java code implementation here
		int nScore = 0;
		int nLength = 0;
		int nAlphaUC = 0;
		int nAlphaLC = 0;
		int nNumber = 0;
		int nSymbol = 0;
		int nMidChar = 0;
		int nRequirements = 0;
		int nAlphasOnly = 0;
		int nNumbersOnly = 0;
		int nUnqChar = 0;
		int nRepChar = 0;
		int nRepInc = 0;
		int nConsecAlphaUC = 0;
		int nConsecAlphaLC = 0;
		int nConsecNumber = 0;
		int nSeqAlpha = 0;
		int nSeqNumber = 0;
		int nSeqSymbol = 0;
		int nReqChar = 0;
		int nMultConsecCharType = 0;
		int nMultRepChar = 1;
		int nMultConsecSymbol = 1;
		int nMultMidChar = 2;
		int nMultRequirements = 2;
		int nMultConsecAlphaUC = 2;
		int nMultConsecAlphaLC = 2;
		int nMultConsecNumber = 2;
		int nReqCharType = 3;
		int nMultAlphaUC = 3;
		int nMultAlphaLC = 3;
		int nMultSeqAlpha = 3;
		int nMultSeqNumber = 3;
		int nMultSeqSymbol = 3;
		int nMultLength = 4;
		int nMultNumber = 4;
		int nMultSymbol = 6;
		String nTmpAlphaUC = "";
		String nTmpAlphaLC = "";
		String nTmpNumber = "";
		String nTmpSymbol = "";
		String sAlphaUC = "0";
		String sAlphaLC = "0";
		String sNumber = "0";
		String sSymbol = "0";
		String sMidChar = "0";
		String sRequirements = "0";
		String sAlphasOnly = "0";
		String sNumbersOnly = "0";
		String sRepChar = "0";
		String sConsecAlphaUC = "0";
		String sConsecAlphaLC = "0";
		String sConsecNumber = "0";
		String sSeqAlpha = "0";
		String sSeqNumber = "0";
		String sSeqSymbol = "0";
		String sAlphas = "abcdefghijklmnopqrstuvwxyz";
		String sNumerics = "01234567890";
		String sSymbols = ")!@#$%^&*()";
		String sComplexity = "Too Short";
		String sStandards = "Below";
		int nMinPwdLen = 8;

		if (pwd != null) {
			nScore = pwd.length() * nMultLength;
			nLength = pwd.length();
			String[] arrPwd = pwd.replaceAll("\\s+", "").split("\\s*");
			int arrPwdLen = arrPwd.length;

			// Loop through password to check for Symbol, Numeric, Lowercase and Uppercase
			// pattern matches
			for (int a = 0; a < arrPwdLen; a++) {
				Pattern alphaUCPattern = Pattern.compile("[A-Z]");
				Matcher alphaUCMatcher = alphaUCPattern.matcher(arrPwd[a]);
				Pattern alphaLCPattern = Pattern.compile("[a-z]");
				Matcher alphaLCMatcher = alphaLCPattern.matcher(arrPwd[a]);
				Pattern numberPattern = Pattern.compile("[0-9]");
				Matcher numberMatcher = numberPattern.matcher(arrPwd[a]);
				Pattern symbolPattern = Pattern.compile("[^a-zA-Z0-9_]");
				Matcher symbolMatcher = symbolPattern.matcher(arrPwd[a]);

				if (alphaUCMatcher.find()) {
					if (!nTmpAlphaUC.isEmpty()) {
						if ((Integer.parseInt(nTmpAlphaUC) + 1) == a) {
							nConsecAlphaUC++;
						}
					}
					nTmpAlphaUC = Integer.toString(a);
					nAlphaUC++;
				} else if (alphaLCMatcher.find()) {
					if (!nTmpAlphaLC.isEmpty()) {
						if ((Integer.parseInt(nTmpAlphaLC) + 1) == a) {
							nConsecAlphaLC++;
						}
					}
					nTmpAlphaLC = Integer.toString(a);
					nAlphaLC++;
				} else if (numberMatcher.find()) {
					if (a > 0 && a < (arrPwdLen - 1)) {
						nMidChar++;
					}
					if (!nTmpNumber.isEmpty()) {
						if ((Integer.parseInt(nTmpNumber) + 1) == a) {
							nConsecNumber++;
						}
					}
					nTmpNumber = Integer.toString(a);
					nNumber++;
				} else if (symbolMatcher.find()) {
					if (a > 0 && a < (arrPwdLen - 1)) {
						nMidChar++;
					}
					if (!nTmpSymbol.isEmpty()) {
						if ((Integer.parseInt(nTmpSymbol) + 1) == a) {
						}
					}
					nTmpSymbol = Integer.toString(a);
					nSymbol++;
				}

				// Internal loop through password to check for repeat characters
				boolean bCharExists = false;
				for (int b = 0; b < arrPwdLen; b++) {
					if (arrPwd[a].equals(arrPwd[b]) && a != b) { // repeat character exists
						bCharExists = true;
						// Calculate increment deduction based on proximity to identical characters
						// Deduction is incremented each time a new match is discovered
						// Deduction amount is based on the total password length divided by the
						// difference of distance between currently selected match
						nRepInc += Math.abs(arrPwdLen / (b - a));
					}
				}
				if (bCharExists) {
					nRepChar++;
					nUnqChar = arrPwdLen - nRepChar;
					nRepInc = (nUnqChar != 0) ? (int) Math.ceil(nRepInc / nUnqChar) : (int) Math.ceil(nRepInc);
				}
			}

			// Check for sequential alpha string patterns (forward and reverse)
			for (int s = 0; s < 23; s++) {
				String sFwd = sAlphas.substring(s, s + 3);
				String sRev = new StringBuilder(sFwd).reverse().toString();
				if (pwd.toLowerCase().contains(sFwd) || pwd.toLowerCase().contains(sRev)) {
					nSeqAlpha++;
				}
			}

			// Check for sequential numeric string patterns (forward and reverse)
			for (int s = 0; s < 8; s++) {
				String sFwd = sNumerics.substring(s, s + 3);
				String sRev = new StringBuilder(sFwd).reverse().toString();
				if (pwd.toLowerCase().contains(sFwd) || pwd.toLowerCase().contains(sRev)) {
					nSeqNumber++;
				}
			}

			// Check for sequential symbol string patterns (forward and reverse)
			for (int s = 0; s < 8; s++) {
				String sFwd = sSymbols.substring(s, s + 3);
				String sRev = new StringBuilder(sFwd).reverse().toString();
				if (pwd.toLowerCase().contains(sFwd) || pwd.toLowerCase().contains(sRev)) {
					nSeqSymbol++;
				}
			}

			// Modify the overall score value based on usage vs requirements
			nScore += nMultRequirements * nRequirements;

			if (nAlphaUC > 0 && nAlphaUC < nLength) {
				nScore += (nLength - nAlphaUC) * 2;
				sAlphaUC = "+ " + (nLength - nAlphaUC) * 2;
			}
			if (nAlphaLC > 0 && nAlphaLC < nLength) {
				nScore += (nLength - nAlphaLC) * 2;
				sAlphaLC = "+ " + (nLength - nAlphaLC) * 2;
			}
			if (nNumber > 0 && nNumber < nLength) {
				nScore += nNumber * nMultNumber;
				sNumber = "+ " + nNumber * nMultNumber;
			}
			if (nSymbol > 0) {
				nScore += nSymbol * nMultSymbol;
				sSymbol = "+ " + nSymbol * nMultSymbol;
			}
			if (nMidChar > 0) {
				nScore += nMidChar * nMultMidChar;
				sMidChar = "+ " + nMidChar * nMultMidChar;
			}

			// Point deductions for poor practices
			if ((nAlphaLC > 0 || nAlphaUC > 0) && nSymbol == 0 && nNumber == 0) {
				// Only Letters
				nScore -= nLength;
				nAlphasOnly = nLength;
				sAlphasOnly = "- " + nLength;
			}
			if (nAlphaLC == 0 && nAlphaUC == 0 && nSymbol == 0 && nNumber > 0) {
				// Only Numbers
				nScore -= nLength;
				nNumbersOnly = nLength;
				sNumbersOnly = "- " + nLength;
			}
			if (nRepChar > 0) {
				// Same character exists more than once
				nScore -= nRepInc;
				sRepChar = "- " + nRepInc;
			}
			if (nConsecAlphaUC > 0) {
				// Consecutive Uppercase Letters exist
				nScore -= nConsecAlphaUC * nMultConsecAlphaUC;
				sConsecAlphaUC = "- " + nConsecAlphaUC * nMultConsecAlphaUC;
			}
			if (nConsecAlphaLC > 0) {
				// Consecutive Lowercase Letters exist
				nScore -= nConsecAlphaLC * nMultConsecAlphaLC;
				sConsecAlphaLC = "- " + nConsecAlphaLC * nMultConsecAlphaLC;
			}
			if (nConsecNumber > 0) {
				// Consecutive Numbers exist
				nScore -= nConsecNumber * nMultConsecNumber;
				sConsecNumber = "- " + nConsecNumber * nMultConsecNumber;
			}
			if (nSeqAlpha > 0) {
				// Sequential alpha strings exist (3 characters or more)
				nScore -= nSeqAlpha * nMultSeqAlpha;
				sSeqAlpha = "- " + nSeqAlpha * nMultSeqAlpha;
			}
			if (nSeqNumber > 0) {
				// Sequential numeric strings exist (3 characters or more)
				nScore -= nSeqNumber * nMultSeqNumber;
				sSeqNumber = "- " + nSeqNumber * nMultSeqNumber;
			}
			if (nSeqSymbol > 0) {
				// Sequential symbol strings exist (3 characters or more)
				nScore -= nSeqSymbol * nMultSeqSymbol;
				sSeqSymbol = "- " + nSeqSymbol * nMultSeqSymbol;
			}

			// Determine if mandatory requirements have been met and set image indicators
			// accordingly
			int[] arrChars = { nLength, nAlphaUC, nAlphaLC, nNumber, nSymbol };
			String[] arrCharsIds = { "nLength", "nAlphaUC", "nAlphaLC", "nNumber", "nSymbol" };
			int arrCharsLen = arrChars.length;
			for (int c = 0; c < arrCharsLen; c++) {
				int minVal = (arrCharsIds[c].equals("nLength")) ? nMinPwdLen - 1 : 0;
				if (arrChars[c] == minVal + 1) {
					nReqChar++;
				} else if (arrChars[c] > minVal + 1) {
					nReqChar++;
				}
			}
			nRequirements = nReqChar;
			int nMinReqChars = (pwd.length() >= nMinPwdLen) ? 3 : 4;
			if (nRequirements > nMinReqChars) { // One or more required characters exist
				nScore += nRequirements * 2;
				sRequirements = "+ " + nRequirements * 2;
			}

			// Determine if additional bonuses need to be applied and set image indicators
			// accordingly
			int[] arrChars2 = { nMidChar, nRequirements };
			String[] arrCharsIds2 = { "nMidChar", "nRequirements" };
			int arrCharsLen2 = arrChars2.length;
			for (int c = 0; c < arrCharsLen2; c++) {
				int minVal = (arrCharsIds2[c].equals("nRequirements")) ? nMinReqChars : 0;
			}

			// Determine if suggested requirements have been met and set image indicators
			// accordingly
			int[] arrChars3 = { nAlphasOnly, nNumbersOnly, nRepChar, nConsecAlphaUC, nConsecAlphaLC, nConsecNumber,
					nSeqAlpha, nSeqNumber, nSeqSymbol };
			String[] arrCharsIds3 = { "nAlphasOnly", "nNumbersOnly", "nRepChar", "nConsecAlphaUC", "nConsecAlphaLC",
					"nConsecNumber", "nSeqAlpha", "nSeqNumber", "nSeqSymbol" };
			int arrCharsLen3 = arrChars3.length;

			// Determine complexity based on the overall score
			if (nScore > 100) {
				nScore = 100;
			} else if (nScore < 0) {
				nScore = 0;
			}
			if (nScore >= 0 && nScore < 20) {
				sComplexity = "Very Weak";
			} else if (nScore >= 20 && nScore < 40) {
				sComplexity = "Weak";
			} else if (nScore >= 40 && nScore < 60) {
				sComplexity = "Good";
			} else if (nScore >= 60 && nScore < 80) {
				sComplexity = "Strong";
			} else if (nScore >= 80 && nScore <= 100) {
				sComplexity = "Very Strong";
			}

			// Display updated score criteria to the client
			System.out.println("Score: " + nScore + "%");
			System.out.println("Complexity: " + sComplexity);

		} else {
			// Display default score criteria to the client
			// You can call the initPwdChk() function here if needed
			System.out.println("Score: " + nScore + "%");
			System.out.println("Complexity: " + sComplexity);
		}

		return sComplexity + ";" + nScore + "%";
	}

    public static String encrypt(String plainText) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(INIT_VECTOR.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);

            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String decrypt(String encryptedText) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(INIT_VECTOR.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes, StandardCharsets.UTF_8);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
