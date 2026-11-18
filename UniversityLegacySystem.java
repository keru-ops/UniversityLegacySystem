import java.util.ArrayList;
import java.util.Scanner;

interface UniversityEntityOperations {
    void registerToSystem();
    void enrollCourse(String courseCode, int units);
    void dropCourse(String courseCode);
    double calculateTuitionOrStipend();
    void processPayment(double amount);
    void applyScholarshipOrGrant(double percentage);
    void issueTranscriptOrRecord();
    void approveClearance();
}

class RegularUndergrad implements UniversityEntityOperations {
    // Exposed public fields (No encapsulation)
    public String id;
    public String fullName;
    public String program;
    public ArrayList<String> courses = new ArrayList<>();
    public int totalUnits = 0;
    public double balance = 0.0;
    public double discountRate = 0.0;
    public boolean cleared = false;

    public RegularUndergrad(String id, String fullName, String program) {
        this.id = id;
        this.fullName = fullName;
        this.program = program;
    }

    @Override
    public void registerToSystem() {
        System.out.println("[REG] Undergrad " + fullName + " (" + id + ") registered under " + program);
    }

    @Override
    public void enrollCourse(String courseCode, int units) {
        if (totalUnits + units > 24) {
            System.out.println("[ERROR] Max unit load (24) exceeded for " + fullName);
            return;
        }
        courses.add(courseCode + " (" + units + "u)");
        totalUnits += units;
        System.out.println("[ENROLL] Added " + courseCode + " to " + fullName);
    }

    @Override
    public void dropCourse(String courseCode) {
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).startsWith(courseCode)) {
                courses.remove(i);
                totalUnits -= 3; // Hardcoded assumption
                System.out.println("[DROP] Dropped " + courseCode + " for " + fullName);
                return;
            }
        }
        System.out.println("[WARN] Course not found.");
    }

    @Override
    public double calculateTuitionOrStipend() {
        double ratePerUnit = 1500.0;
        double miscFee = 4500.0;
        double gross = (totalUnits * ratePerUnit) + miscFee;
        balance = gross - (gross * discountRate);
        return balance;
    }

    @Override
    public void processPayment(double amount) {
        if (amount <= 0) {
            System.out.println("[ERROR] Invalid payment amount.");
            return;
        }
        balance -= amount;
        System.out.println("[BILLING] Payment of PHP " + amount + " received. Remaining balance: PHP " + balance);
    }

    @Override
    public void applyScholarshipOrGrant(double percentage) {
        this.discountRate = percentage;
        System.out.println("[GRANT] Applied " + (percentage * 100) + "% discount to " + fullName);
    }

    @Override
    public void issueTranscriptOrRecord() {
        System.out.println("=== OFFICIAL RECORD: " + fullName + " [" + id + "] ===");
        System.out.println("Program: " + program + " | Units: " + totalUnits);
        System.out.println("Courses: " + courses);
        System.out.println("Current Balance: PHP " + balance);
    }

    @Override
    public void approveClearance() {
        if (balance <= 0) {
            cleared = true;
            System.out.println("[CLEARANCE] " + fullName + " is CLEARED for exams.");
        } else {
            cleared = false;
            System.out.println("[CLEARANCE] HOLD: " + fullName + " still owes PHP " + balance);
        }
    }
}

class GraduateStudent implements UniversityEntityOperations {
    // Exposed public fields duplicated from RegularUndergrad
    public String id;
    public String fullName;
    public String thesisTopic;
    public ArrayList<String> courses = new ArrayList<>();
    public int totalUnits = 0;
    public double balance = 0.0;
    public double researchGrant = 0.0;
    public boolean cleared = false;

    public GraduateStudent(String id, String fullName, String thesisTopic) {
        this.id = id;
        this.fullName = fullName;
        this.thesisTopic = thesisTopic;
    }

    @Override
    public void registerToSystem() {
        System.out.println("[REG] Grad Student " + fullName + " (" + id + ") | Thesis: " + thesisTopic);
    }

    @Override
    public void enrollCourse(String courseCode, int units) {
        if (totalUnits + units > 12) {
            System.out.println("[ERROR] Grad max unit load (12) exceeded for " + fullName);
            return;
        }
        courses.add(courseCode + " (" + units + "u)");
        totalUnits += units;
        System.out.println("[ENROLL] Added grad course " + courseCode + " to " + fullName);
    }

    @Override
    public void dropCourse(String courseCode) {
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).startsWith(courseCode)) {
                courses.remove(i);
                totalUnits -= 3;
                System.out.println("[DROP] Dropped grad course " + courseCode);
                return;
            }
        }
    }

    @Override
    public double calculateTuitionOrStipend() {
        double ratePerUnit = 2800.0;
        double labAndLibraryFee = 6000.0;
        double gross = (totalUnits * ratePerUnit) + labAndLibraryFee;
        balance = gross - researchGrant;
        if (balance < 0) balance = 0;
        return balance;
    }

    @Override
    public void processPayment(double amount) {
        if (amount <= 0) {
            System.out.println("[ERROR] Invalid payment amount.");
            return;
        }
        balance -= amount;
        System.out.println("[BILLING] Grad payment of PHP " + amount + " received. Remaining: PHP " + balance);
    }

    @Override
    public void applyScholarshipOrGrant(double flatAmount) {
        this.researchGrant = flatAmount;
        System.out.println("[GRANT] Applied research grant of PHP " + flatAmount + " to " + fullName);
    }

    @Override
    public void issueTranscriptOrRecord() {
        System.out.println("=== GRADUATE RECORD: " + fullName + " [" + id + "] ===");
        System.out.println("Thesis: " + thesisTopic + " | Units: " + totalUnits);
        System.out.println("Courses: " + courses);
        System.out.println("Current Balance: PHP " + balance);
    }

    @Override
    public void approveClearance() {
        if (balance <= 0) {
            cleared = true;
            System.out.println("[CLEARANCE] Grad student " + fullName + " is CLEARED.");
        } else {
            cleared = false;
            System.out.println("[CLEARANCE] HOLD: Balance remaining PHP " + balance);
        }
    }
}

class InternationalExchangeStudent implements UniversityEntityOperations {
    public String id;
    public String fullName;
    public String homeUniversity;
    public String visaStatus;
    public ArrayList<String> courses = new ArrayList<>();
    public int totalUnits = 0;
    public double balance = 0.0;
    public boolean cleared = false;

    public InternationalExchangeStudent(String id, String fullName, String homeUniversity, String visaStatus) {
        this.id = id;
        this.fullName = fullName;
        this.homeUniversity = homeUniversity;
        this.visaStatus = visaStatus;
    }

    @Override
    public void registerToSystem() {
        System.out.println("[REG] Exchange Student " + fullName + " from " + homeUniversity + " (Visa: " + visaStatus + ")");
    }

    @Override
    public void enrollCourse(String courseCode, int units) {
        if (!visaStatus.equalsIgnoreCase("ACTIVE")) {
            System.out.println("[ERROR] Cannot enroll. Visa is not ACTIVE.");
            return;
        }
        courses.add(courseCode + " (" + units + "u)");
        totalUnits += units;
        System.out.println("[ENROLL] Exchange student enrolled in " + courseCode);
    }

    @Override
    public void dropCourse(String courseCode) {
        courses.removeIf(c -> c.startsWith(courseCode));
        System.out.println("[DROP] Removed " + courseCode + " from exchange record.");
    }

    @Override
    public double calculateTuitionOrStipend() {
        double adminFee = 5000.0;
        double foreignStudentSurcharge = 7500.0;
        balance = adminFee + foreignStudentSurcharge;
        return balance;
    }

    @Override
    public void processPayment(double amount) {
        balance -= amount;
        System.out.println("[BILLING] Foreign wire/cash payment of PHP " + amount + ". Remaining: PHP " + balance);
    }

    @Override
    public void applyScholarshipOrGrant(double percentage) {
        balance -= (7500.0 * percentage);
        System.out.println("[GRANT] Applied partner MOU discount to surcharge.");
    }

    @Override
    public void issueTranscriptOrRecord() {
        System.out.println("=== EXCHANGE RECORD: " + fullName + " (" + homeUniversity + ") ===");
        System.out.println("Visa: " + visaStatus + " | Courses: " + courses + " | Due: PHP " + balance);
    }

    @Override
    public void approveClearance() {
        if (balance <= 0 && visaStatus.equalsIgnoreCase("ACTIVE")) {
            cleared = true;
            System.out.println("[CLEARANCE] Exchange student cleared for departure/exams.");
        } else {
            System.out.println("[CLEARANCE] HOLD: Check balance or visa status.");
        }
    }
}

class TeachingAssistant implements UniversityEntityOperations {
    public String id;
    public String fullName;
    public String assignedCourse;
    public ArrayList<String> enrolledCourses = new ArrayList<>();
    public int units = 0;
    public double tuitionBalance = 0.0;
    public double monthlyStipend = 18000.0;
    public boolean cleared = false;

    public TeachingAssistant(String id, String fullName, String assignedCourse) {
        this.id = id;
        this.fullName = fullName;
        this.assignedCourse = assignedCourse;
    }

    @Override
    public void registerToSystem() {
        System.out.println("[REG] Teaching Assistant " + fullName + " assigned to assist " + assignedCourse);
    }

    @Override
    public void enrollCourse(String courseCode, int u) {
        enrolledCourses.add(courseCode + " (" + u + "u)");
        units += u;
        System.out.println("[ENROLL] TA " + fullName + " enrolled in " + courseCode);
    }

    @Override
    public void dropCourse(String courseCode) {
        enrolledCourses.removeIf(c -> c.startsWith(courseCode));
        System.out.println("[DROP] TA dropped " + courseCode);
    }

    @Override
    public double calculateTuitionOrStipend() {
        double grossTuition = (units * 2800.0) + 6000.0;
        tuitionBalance = grossTuition - monthlyStipend;
        return tuitionBalance;
    }

    @Override
    public void processPayment(double amount) {
        tuitionBalance -= amount;
        System.out.println("[BILLING] TA Ledger adjusted by PHP " + amount + ". Net Balance: PHP " + tuitionBalance);
    }

    @Override
    public void applyScholarshipOrGrant(double percentage) {
        monthlyStipend += (monthlyStipend * percentage);
        System.out.println("[PAYROLL] Increased TA stipend to PHP " + monthlyStipend);
    }

    @Override
    public void issueTranscriptOrRecord() {
        System.out.println("=== TA RECORD: " + fullName + " (Assisting: " + assignedCourse + ") ===");
        System.out.println("Enrolled: " + enrolledCourses + " | Net Ledger: PHP " + tuitionBalance);
    }

    @Override
    public void approveClearance() {
        if (tuitionBalance <= 0) {
            cleared = true;
            System.out.println("[CLEARANCE] TA " + fullName + " cleared.");
        } else {
            System.out.println("[CLEARANCE] HOLD: TA owes PHP " + tuitionBalance);
        }
    }
}

class ExternalAuditor implements UniversityEntityOperations {
    public String id;
    public String fullName;
    public String organization;
    public ArrayList<String> auditedCourses = new ArrayList<>();
    public double flatFeeBalance = 0.0;

    public ExternalAuditor(String id, String fullName, String organization) {
        this.id = id;
        this.fullName = fullName;
        this.organization = organization;
    }

    @Override
    public void registerToSystem() {
        System.out.println("[REG] External Auditor " + fullName + " from " + organization);
    }

    @Override
    public void enrollCourse(String courseCode, int units) {
        auditedCourses.add(courseCode + " [AUDIT]");
        System.out.println("[AUDIT] Added " + courseCode + " to audit list.");
    }

    @Override
    public void dropCourse(String courseCode) {
        auditedCourses.removeIf(c -> c.startsWith(courseCode));
        System.out.println("[AUDIT] Removed " + courseCode);
    }

    @Override
    public double calculateTuitionOrStipend() {
        flatFeeBalance = auditedCourses.size() * 2000.0;
        return flatFeeBalance;
    }

    @Override
    public void processPayment(double amount) {
        flatFeeBalance -= amount;
        System.out.println("[BILLING] Audit fee paid: PHP " + amount + ". Remaining: PHP " + flatFeeBalance);
    }

    @Override
    public void applyScholarshipOrGrant(double percentage) {
        flatFeeBalance -= (flatFeeBalance * percentage);
        System.out.println("[BILLING] Corporate partner discount applied.");
    }

    @Override
    public void issueTranscriptOrRecord() {
        System.out.println("=== CERTIFICATE OF ATTENDANCE (NO CREDIT): " + fullName + " ===");
        System.out.println("Audited Courses: " + auditedCourses);
    }

    @Override
    public void approveClearance() {
        System.out.println("[CLEARANCE] Library/Campus pass " + (flatFeeBalance <= 0 ? "ACTIVE" : "SUSPENDED"));
    }
}

public class UniversityLegacySystem {

    public static ArrayList<UniversityEntityOperations> database = new ArrayList<>();
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        
        database.add(new RegularUndergrad("2026-001", "Maria Santos", "BS Information Technology"));
        database.add(new GraduateStudent("2026-002", "Juan Dela Cruz", "AI in Distributed Systems"));
        database.add(new InternationalExchangeStudent("2026-003", "Kenji Takahashi", "Tokyo Tech", "ACTIVE"));
        database.add(new TeachingAssistant("2026-004", "Elena Reyes", "CS101 - Intro to Programming"));
        database.add(new ExternalAuditor("2026-005", "Carlos Mendoza", "Department of Science and Tech"));

        boolean running = true;

        while (running) {
            System.out.println("\n=======================================================");
            System.out.println("   UNIVERSITY ENROLLMENT & BILLING SYSTEM (LEGACY v1)  ");
            System.out.println("=======================================================");
            System.out.println("1. List All Registered Records");
            System.out.println("2. Register New Person");
            System.out.println("3. Enroll Course");
            System.out.println("4. Assess Tuition / Billing & Apply Grant");
            System.out.println("5. Process Payment");
            System.out.println("6. View Official Record & Check Clearance");
            System.out.println("7. Special Administrative Update");
            System.out.println("0. Exit System");
            System.out.print("Select option: ");

            String choice = scanner.nextLine();

            // MASSIVE IF-ELSE BLOCKS & NAIVE DOWNCASTING EVERYWHERE
            if (choice.equals("1")) {
                for (int i = 0; i < database.size(); i++) {
                    UniversityEntityOperations entity = database.get(i);
                    // Naive downcasting without instanceof checks in unsafe paths or repetitive type checks
                    if (entity instanceof RegularUndergrad) {
                        RegularUndergrad u = (RegularUndergrad) entity;
                        System.out.println(i + ": [UNDERGRAD] " + u.id + " - " + u.fullName + " (" + u.program + ")");
                    } else if (entity instanceof GraduateStudent) {
                        GraduateStudent g = (GraduateStudent) entity;
                        System.out.println(i + ": [GRAD] " + g.id + " - " + g.fullName + " (Thesis: " + g.thesisTopic + ")");
                    } else if (entity instanceof InternationalExchangeStudent) {
                        InternationalExchangeStudent ex = (InternationalExchangeStudent) entity;
                        System.out.println(i + ": [EXCHANGE] " + ex.id + " - " + ex.fullName + " (" + ex.homeUniversity + ")");
                    } else if (entity instanceof TeachingAssistant) {
                        TeachingAssistant ta = (TeachingAssistant) entity;
                        System.out.println(i + ": [TA] " + ta.id + " - " + ta.fullName + " (Assisting: " + ta.assignedCourse + ")");
                    } else if (entity instanceof ExternalAuditor) {
                        ExternalAuditor au = (ExternalAuditor) entity;
                        System.out.println(i + ": [AUDITOR] " + au.id + " - " + au.fullName + " (" + au.organization + ")");
                    }
                }
            } else if (choice.equals("2")) {
                System.out.print("Type (1=Undergrad, 2=Grad, 3=Exchange, 4=TA, 5=Auditor): ");
                String type = scanner.nextLine();
                System.out.print("Enter ID: ");
                String id = scanner.nextLine();
                System.out.print("Enter Full Name: ");
                String name = scanner.nextLine();

                if (type.equals("1")) {
                    System.out.print("Enter Degree Program: ");
                    String prog = scanner.nextLine();
                    RegularUndergrad u = new RegularUndergrad(id, name, prog);
                    u.registerToSystem();
                    database.add(u);
                } else if (type.equals("2")) {
                    System.out.print("Enter Thesis Topic: ");
                    String topic = scanner.nextLine();
                    GraduateStudent g = new GraduateStudent(id, name, topic);
                    g.registerToSystem();
                    database.add(g);
                } else if (type.equals("3")) {
                    System.out.print("Enter Home University: ");
                    String uni = scanner.nextLine();
                    System.out.print("Enter Visa Status (ACTIVE/EXPIRED): ");
                    String visa = scanner.nextLine();
                    InternationalExchangeStudent ex = new InternationalExchangeStudent(id, name, uni, visa);
                    ex.registerToSystem();
                    database.add(ex);
                } else if (type.equals("4")) {
                    System.out.print("Enter Assigned Course to Assist: ");
                    String course = scanner.nextLine();
                    TeachingAssistant ta = new TeachingAssistant(id, name, course);
                    ta.registerToSystem();
                    database.add(ta);
                } else if (type.equals("5")) {
                    System.out.print("Enter External Organization: ");
                    String org = scanner.nextLine();
                    ExternalAuditor au = new ExternalAuditor(id, name, org);
                    au.registerToSystem();
                    database.add(au);
                } else {
                    System.out.println("[ERROR] Unknown type.");
                }
            } else if (choice.equals("3")) {
                UniversityEntityOperations entity = selectEntity();
                if (entity != null) {
                    System.out.print("Course Code (e.g., IT202): ");
                    String code = scanner.nextLine();
                    System.out.print("Units: ");
                    int units = Integer.parseInt(scanner.nextLine());
                    entity.enrollCourse(code, units);
                }
            } else if (choice.equals("4")) {
                UniversityEntityOperations entity = selectEntity();
                if (entity != null) {
                    double initialDue = entity.calculateTuitionOrStipend();
                    System.out.println("[ASSESSMENT] Initial Computed Amount: PHP " + initialDue);
                    System.out.print("Apply Grant/Discount? (y/n): ");
                    if (scanner.nextLine().equalsIgnoreCase("y")) {
                        // Inconsistent parameter behavior exposed to caller!
                        if (entity instanceof GraduateStudent) {
                            System.out.print("Enter flat research grant in PHP: ");
                        } else {
                            System.out.print("Enter discount rate (e.g., 0.20 for 20%): ");
                        }
                        double val = Double.parseDouble(scanner.nextLine());
                        entity.applyScholarshipOrGrant(val);
                        System.out.println("[ASSESSMENT] Updated Amount: PHP " + entity.calculateTuitionOrStipend());
                    }
                }
            } else if (choice.equals("5")) {
                UniversityEntityOperations entity = selectEntity();
                if (entity != null) {
                    System.out.print("Enter Payment Amount (PHP): ");
                    double amt = Double.parseDouble(scanner.nextLine());
                    entity.processPayment(amt);
                }
            } else if (choice.equals("6")) {
                UniversityEntityOperations entity = selectEntity();
                if (entity != null) {
                    entity.issueTranscriptOrRecord();
                    entity.approveClearance();
                }
            } else if (choice.equals("7")) {
                System.out.print("Enter Record Index to modify special attribute: ");
                int idx = Integer.parseInt(scanner.nextLine());
                UniversityEntityOperations target = database.get(idx);

                System.out.println("1. Update Thesis Topic (Grad Only)");
                System.out.println("2. Update Visa Status (Exchange Only)");
                System.out.println("3. Update Stipend Amount (TA Only)");
                String sub = scanner.nextLine();

                if (sub.equals("1")) {
                    GraduateStudent grad = (GraduateStudent) target;
                    System.out.print("New Thesis Topic: ");
                    grad.thesisTopic = scanner.nextLine();
                    System.out.println("Updated successfully.");
                } else if (sub.equals("2")) {
                    InternationalExchangeStudent ex = (InternationalExchangeStudent) target;
                    System.out.print("New Visa Status: ");
                    ex.visaStatus = scanner.nextLine();
                    System.out.println("Updated successfully.");
                } else if (sub.equals("3")) {
                    TeachingAssistant ta = (TeachingAssistant) target;
                    System.out.print("New Monthly Stipend: ");
                    ta.monthlyStipend = Double.parseDouble(scanner.nextLine());
                    System.out.println("Updated successfully.");
                }
            } else if (choice.equals("0")) {
                running = false;
                System.out.println("Shutting down University Legacy System...");
            } else {
                System.out.println("Invalid option.");
            }
        }
        scanner.close();
    }

    public static UniversityEntityOperations selectEntity() {
        System.out.print("Enter Record Index (0 to " + (database.size() - 1) + "): ");
        int idx = Integer.parseInt(scanner.nextLine());
        if (idx < 0 || idx >= database.size()) {
            System.out.println("[ERROR] Invalid index.");
            return null;
        }
        return database.get(idx);
    }
}