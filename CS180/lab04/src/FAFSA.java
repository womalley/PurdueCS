/**
 * CS180 - Lab 04 - FAFSA
 * The program determines if the student is eligible for financial aid and then determines how much.
 * @author William O'Malley, womalley@purdue.edu
 *
 * Created by womalley on 2/3/16.
 */
public class FAFSA {
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

    public FAFSA(boolean isAcceptedStudent, boolean isSSregistered, boolean hasSSN,
                 boolean hasValidResidency, boolean isDependent, int age,
                 int creditHours, double studentIncome, double parentIncome,
                 String classStanding) {
        this.isAcceptedStudent = isAcceptedStudent;
        this.isSSregistered = isSSregistered;
        this.hasSSN = hasSSN;
        this.hasValidResidency = hasValidResidency;
        this.isDependent = isDependent;
        this.age = age;
        this.creditHours = creditHours;
        this.studentIncome = studentIncome;
        this.parentIncome = parentIncome;
        this.classStanding = classStanding;
    }
    /**
     * Determines if the student is eligible for federal financial aid. To be
     * eligible, the student must be an accepted student to a higher education
     * program (isAcceptedStudent), must be registered with the selective service
     * (isSSregistered) if they are between  the ages of 18-25 inclusive, must
     * have a social security number (hasSSN), and must have valid residency
     * status (hasValidResidency).
     *
     * @return True if the student is aid eligible and false otherwise
     */
    public boolean isFederalAidEligible() {
        if(isAcceptedStudent && hasSSN && hasValidResidency) {
            if(age >= 18 && age <= 25 && isSSregistered) {
                return (true);
            }
            else if(age < 18 || age > 25){
                return (true);
            }
            else {
                return (false);
            }
        }
        else {
            return (false);
        }
    }
    /**
     * Calculates the students expected family contribution. If the student is
     * a dependent, then their EFC is calculated by the sum of the students
     * income and their parent's income, else if it just the student's income.
     *
     * @return The students expected family contribution
     */
    public double calcEFC() {
        double efc;

        if(isDependent) {
            efc = studentIncome + parentIncome;
        } else {
            efc = studentIncome;
        }
        return (efc);
    }
    /**
     * Calculates the student's federal grant award. The student's EFC must be
     * less than or equal to 50000 and they must be an undergraduate. The award
     * amount is based on their class standing and credit hours. Refer to the
     * tables in the breakdown section.
     *
     * @return The student's calculated federal grant award amount
     */
    public double calcFederalGrant() {
        if(classStanding == null) {
            return (0);
        }
        else if(calcEFC() <= 50000 && classStanding.toLowerCase().equals("undergraduate")) {
            if (creditHours < 9) {
                return (2500);
            }
            else {
                return (5775);
            }
        }
        else {
            return (0);
        }
    }
    /**
     * Calculates the student's total Stafford loan award. The Stafford loan is
     * only available for students registered for 9 or more credit hours. The amount
     * of the award is calculated by the student's dependency status and their
     * class standing. Refer to the tables in the breakdown section.
     *
     * @return The student's calculated Stafford loan award amount
     */
    public double calcStaffordLoan() {
        if(classStanding == null) {
            return (0);
        }
        else if(creditHours >= 9) {
            if (isDependent) {
                if (classStanding.toLowerCase().equals("undergraduate")) {
                    return (5000);
                }
                else if (classStanding.toLowerCase().equals("graduate")) {
                    return (10000);
                }
                else {
                    return (0);
                }
            }
            else {
                if (classStanding.toLowerCase().equals("undergraduate")) {
                    return (10000);
                }
                else if (classStanding.toLowerCase().equals("graduate")) {
                    return (20000);
                }
                else {
                    return (0);
                }
            }
        }
        else {
            return (0);
        }
    }
    /**
     * Calculates the student's work study award. The work study award is
     * based on the student's EFC. Refer to the table in the breakdown section.
     *
     * @return The student's calculated federal grant award amount
     */
    public double calcWorkStudy() {
        double efc = calcEFC();

        if(efc >= 0.00 && efc <= 30000.00) {
            return (1500);
        }
        else if(efc >= 30000.01 && efc <= 40000.00) {
            return (1000);
        }
        else if(efc >= 40000.01 && efc <= 50000.00) {
            return (500);
        }
        else if(efc >= 50000.00) {
            return(0);
        }
        else {
            return(-1);
        }
    }
    /**
     * Calculates the student's total combined federal aid award. The total
     * aid award is calculated as the sum of Stafford loan award, federal grant
     * award, and work study award. You should make a call to the method
     * isFederalAidEligible() to see if the student is eligible to receive
     * federal aid. If they are NOT eligible, you can return 0. If the are, you
     * will return their total aid award.
     *
     * @return The student's total aid award amount
     */
    public double calcFederalAidAmount() {
        double totalAid;

        if(isFederalAidEligible()) {
            totalAid = calcFederalGrant() + calcStaffordLoan() + calcWorkStudy();
            return(totalAid);
        }
        else {
            return (0);
        }
    }
}

