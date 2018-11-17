import javax.swing.JOptionPane;

/**
 * Created by bomal_000 on 3/29/2016.
 */
public class FAFSAGUI {

    public static void main(String[] args) {

        //Variables from FAFSA
        boolean isAcceptedStudent;
        boolean isSSregistered;
        boolean hasSSN;
        boolean hasValidResidency;
        boolean isDependent;
        int age;
        int creditHours;
        double studentIncome;
        double parentIncome;
        String classStanding;

        //Variables for FAFSAGUI
        Object message;
        String title;
        int messageType;
        boolean cont = true;

        double loan;
        double grant;
        double workStudy;
        double totalAwards;
        String results;

        do {
            //dialog 1
            JOptionPane.showMessageDialog(null, "Welcome to the FAFSA!", "Welcome", JOptionPane.INFORMATION_MESSAGE);

            //dialog 2
            messageType = JOptionPane.showOptionDialog(null, "Have you been accepted into a degree or certificate " +
                            "program?", "Program Acceptance", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    null, null);

            if (messageType == JOptionPane.YES_OPTION) {
                isAcceptedStudent = true;
            } else {
                isAcceptedStudent = false;
            }

            //dialog 3
            messageType = JOptionPane.showOptionDialog(null, "Are you registered for the selective service?",
                    "Selective " +
                    "Service", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            if (messageType == JOptionPane.YES_OPTION) {
                isSSregistered = true;
            } else {
                isSSregistered = false;
            }

            //dialog 4
            messageType = JOptionPane.showOptionDialog(null, "Do you have a social security number?", "Social Security " +
                    "Number", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            if (messageType == JOptionPane.YES_OPTION) {
                hasSSN = true;
            } else {
                hasSSN = false;
            }

            //dialog 5
            messageType = JOptionPane.showOptionDialog(null, "Do you have valid residency status?", "Residency Status",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            if (messageType == JOptionPane.YES_OPTION) {
                hasValidResidency = true;
            } else {
                hasValidResidency = false;
            }

            //dialog 6
            do {
                title = JOptionPane.showInputDialog(null, "How old are you?", "Age", JOptionPane.QUESTION_MESSAGE);

                messageType = Integer.parseInt(title);
                if (messageType < 0) {
                    JOptionPane.showMessageDialog(null, "Age cannot be a negative number", "Error: Age", JOptionPane
                            .ERROR_MESSAGE);
                }
            } while (messageType < 0);

            age = (messageType);

            //dialog 7
            do {
                title = JOptionPane.showInputDialog(null, "How many credit hours do you plan on taking?", "Credit " +
                        "Hours",
                        JOptionPane.QUESTION_MESSAGE);

                messageType = Integer.parseInt(title);
                if (messageType < 1 || messageType > 24) {
                    JOptionPane.showMessageDialog(null, "Credit hours must be between 1 and 24, inclusive.", "Error: " +
                            "Credit Hours", JOptionPane.ERROR_MESSAGE);
                }
            } while (messageType < 1 || messageType > 24);

            creditHours = (messageType);

            //dialog 8
            do {
                title = JOptionPane.showInputDialog(null, "What is your total yearly income?", "Student Income",
                        JOptionPane.QUESTION_MESSAGE);

                messageType = Integer.parseInt(title);
                if (messageType < 0) {
                    JOptionPane.showMessageDialog(null, "Income cannot be a negative number.", "Error: Student Income",
                            JOptionPane.ERROR_MESSAGE);
                }
            } while (messageType < 0);

            studentIncome = (messageType);

            //dialog 9
            do {
                title = JOptionPane.showInputDialog(null, "What is your parent's total yearly income?", "Parent Income",
                        JOptionPane.QUESTION_MESSAGE);

                messageType = Integer.parseInt(title);
                if (messageType < 0) {
                    JOptionPane.showMessageDialog(null, "Income cannot be a negative number.", "Error: Parent Income",
                            JOptionPane.ERROR_MESSAGE);
                }
            } while (messageType < 0);

            parentIncome = (messageType);

            //dialog 10
            messageType = JOptionPane.showOptionDialog(null, "Are you a dependent?", "Dependency", JOptionPane
                    .YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            if (messageType == JOptionPane.YES_OPTION) {
                isDependent = true;
            }
            else {
                isDependent = false;
            }

            //dialog 11
            String [] classYear = {"Freshman", "Sophmore", "Junior", "Senior", "Graduate"};

            message = JOptionPane.showInputDialog(null, "What is your current class standing?", "Class Standing",
                    JOptionPane.PLAIN_MESSAGE, null, classYear, null);

            if (message.equals("Freshman") || message.equals("Sophmore") || message.equals("Junior") || message.equals
                    ("Senior")) {
                classStanding = ("undergraduate");
            }
            else {
                classStanding = ("graduate");
            }

            //dialog 12
            FAFSA newFafsa = new FAFSA(isAcceptedStudent, isSSregistered, hasSSN, hasValidResidency, isDependent,
                    age, creditHours, studentIncome, parentIncome, classStanding);

            //dialog 13
            loan = newFafsa.calcStaffordLoan();
            grant = newFafsa.calcFederalGrant();
            workStudy = newFafsa.calcWorkStudy();
            totalAwards = newFafsa.calcFederalAidAmount();

            //dialog 14

            results = String.format(" Loans: $ %30.2f\n Grants: $ %33.2f\n Work Study: $ %24.2f\n\n Total Awards: $ %" +
                    "17.2f", loan, grant, workStudy, totalAwards);

            JOptionPane.showMessageDialog(null, results, "FAFSA Results", JOptionPane.INFORMATION_MESSAGE);

            //dialog 15
            messageType = JOptionPane.showOptionDialog(null, "Would you like to complete another Application?",
                    "Continue", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            if (messageType == JOptionPane.YES_OPTION) {
                cont = true;
            } else {
                cont = false;
            }

        } while (cont);
    }
}
