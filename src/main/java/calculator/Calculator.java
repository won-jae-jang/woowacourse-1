package calculator;

public class Calculator {

    private final String leftCustomSeparator = "//";
    private final String rightCustomSeparator = "\\n";
    private int result = 0;

    public int getSumValueInFormulation(String formulation) {
        String customSeparator = getCustomSeparator(formulation);
        String regex = getRegex(customSeparator);

        if (customSeparator != null) {
            formulation = removeCustomSeparator(formulation);
        }

        String[] splitArr = formulation.split(regex);
        for (String stringNumber : splitArr) {
            try {
                if (stringNumber.isEmpty()) continue;
                int number = Integer.parseInt(stringNumber);
                if (number < 0) throw new IllegalArgumentException();
                result += number;
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException();
            }
        }
        return result;
    }

    /**
     * @param customSeparator: //;\n 에서 customSeparator 는 ; 가 된다.
     * 기본 구분자는 , : 이므로 [,:]의 정규식을 만든다.
     * 구분자를 추가하고자 하는 경우 [customSeparator , :] 형태가 된다.
     * @return: 정규식 포맷을 반환한다.
     */
    private String getRegex(String customSeparator) {
        StringBuilder regex = new StringBuilder();
        //구분자들을 포함하는 정규식 만들기
        if (customSeparator == null) {
            regex.append("[,:]");
        } else {
            regex.append("[,:").append(customSeparator).append("]");
        }
        return regex.toString();
    }

    private String getCustomSeparator(String formulation) {
        StringBuilder sb = new StringBuilder();
        if (formulation.contains(leftCustomSeparator) && formulation.contains(rightCustomSeparator)) {
            int startIdx = formulation.indexOf(leftCustomSeparator);
            int endIdx = formulation.indexOf(rightCustomSeparator);
            /*
              formulation 의 //;\n 중에서 ; 를 추출해야 한다.
              따라서 index 2(0 + 2)번부터 커스텀 구분자를 추출 시작해야 한다.
            */
            for (int i = startIdx + 2; i < endIdx; i++) {
                sb.append(formulation.charAt(i));
            }
            return sb.toString();
        } else {
            return null;
        }
    }

    private String removeCustomSeparator(String formulation) {
        StringBuilder sb = new StringBuilder();
        if (formulation.contains(leftCustomSeparator) && formulation.contains(rightCustomSeparator)) {
            /*
            * formulation 의 //;\n1 에서 필요 없는 //;\n 를 제거해야 한다.
            * 따라서 오른쪽 커스텀 문자인 \n 의 인덱스에서 2를 더한 곳부터 식을 추출하면 된다.
            */
            int endIdx = formulation.indexOf(rightCustomSeparator);
            for (int i = endIdx + 2; i < formulation.length(); i++) {
                sb.append(formulation.charAt(i));
            }
            return sb.toString();
        } else {
            return formulation;
        }
    }
}
