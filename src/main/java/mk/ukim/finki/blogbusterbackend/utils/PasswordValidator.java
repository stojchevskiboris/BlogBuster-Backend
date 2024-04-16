//package mk.ukim.finki.blogbusterbackend.utils;
//
//public class PasswordValidator {
//
//    private static final String SPECIAL_CHARACTERS = "~!@#$%^&*()_+{}\":;'[]";
//    private static final int MIN_LENGTH_DIGIT = 1;
//    private static final int MIN_LENGTH_SPECIAL = 1;
//    private static final int MIN_LENGTH_UPPER = 1;
//    private static final int MIN_LENGTH = 8;
//
//    public boolean isValid(String password) {
//        return hasMinimumLength(password, MIN_LENGTH) &&
//                hasMinimumDigits(password, MIN_LENGTH_DIGIT) &&
//                hasMinimumSpecialChars(password, MIN_LENGTH_SPECIAL) &&
//                hasMinimumUppercaseChars(password, MIN_LENGTH_UPPER);
//    }
//
//    private boolean hasMinimumLength(String password, int minLength) {
//        return password.length() >= minLength;
//    }
//
//    private boolean hasMinimumDigits(String password, int minDigits) {
//        int count = 0;
//        for (char c : password.toCharArray()) {
//            if (Character.isDigit(c)) {
//                count++;
//            }
//            if (count >= minDigits) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    private boolean hasMinimumSpecialChars(String password, int minSpecialChars) {
//        int count = 0;
//        for (char c : password.toCharArray()) {
//            if (SPECIAL_CHARACTERS.indexOf(c) != -1) {
//                count++;
//            }
//            if (count >= minSpecialChars) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    private boolean hasMinimumUppercaseChars(String password, int minLengthUpper) {
//        int count = 0;
//        for (char c : password.toCharArray()) {
//            if (Character.isUpperCase(c)) {
//                count++;
//            }
//            if (count >= minLengthUpper) {
//                return true;
//            }
//        }
//        return false;
//    }
//}
