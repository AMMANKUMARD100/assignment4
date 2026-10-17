import java.util.Scanner;

public class ContactDirectoryBST {

    // Node representing each Contact
    static class ContactNode {
        String name;
        String phone;
        String email;
        ContactNode left, right;

        ContactNode(String name, String phone, String email) {
            this.name = name.trim();
            this.phone = phone.trim();
            this.email = email == null ? "" : email.trim();
        }

        @Override
        public String toString() {
            String emailStr = (email.isEmpty()) ? "" : " | Email: " + email;
            return "Name: " + name + " | Phone: " + phone + emailStr;
        }
    }

    private ContactNode root;

    public boolean insert(String name, String phone, String email) {
        if (name == null || name.trim().isEmpty() || phone == null || phone.trim().isEmpty()) {
            System.out.println("Error: Name and phone cannot be empty.");
            return false;
        }

        name = name.trim();
        phone = phone.trim();
        email = email == null ? "" : email.trim();

        if (root == null) {
            root = new ContactNode(name, phone, email);
            System.out.println("-> Successfully added contact: " + name);
            return true;
        }

        ContactNode curr = root;
        while (curr != null) {
            int cmp = name.compareToIgnoreCase(curr.name);
            if (cmp == 0) {
                curr.phone = phone;
                curr.email = email;
                System.out.println("-> Contact '" + name + "' already exists. Details updated!");
                return true;
            } else if (cmp < 0) {
                if (curr.left == null) {
                    curr.left = new ContactNode(name, phone, email);
                    System.out.println("-> Successfully added contact: " + name);
                    return true;
                }
                curr = curr.left;
            } else {
                if (curr.right == null) {
                    curr.right = new ContactNode(name, phone, email);
                    System.out.println("-> Successfully added contact: " + name);
                    return true;
                }
                curr = curr.right;
            }
        }
        return false;
    }

    public ContactNode search(String name) {
        if (name == null || name.trim().isEmpty()) return null;
        String target = name.trim();
        ContactNode curr = root;
        while (curr != null) {
            int cmp = target.compareToIgnoreCase(curr.name);
            if (cmp == 0) return curr;
            else if (cmp < 0) curr = curr.left;
            else curr = curr.right;
        }
        return null;
    }

    private ContactNode minValueNode(ContactNode node) {
        ContactNode current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    private boolean deleteSuccess = false;

    public boolean delete(String name) {
        if (name == null || name.trim().isEmpty()) return false;
        deleteSuccess = false;
        root = deleteRec(root, name.trim());
        return deleteSuccess;
    }

    private ContactNode deleteRec(ContactNode node, String name) {
        if (node == null) return null;

        int cmp = name.compareToIgnoreCase(node.name);
        if (cmp < 0) {
            node.left = deleteRec(node.left, name);
        } else if (cmp > 0) {
            node.right = deleteRec(node.right, name);
        } else {
            // Found node to delete
            deleteSuccess = true;

            // Case 1 & 2: 0 or 1 child
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;

            // Case 3: 2 children - get inorder successor
            ContactNode successor = minValueNode(node.right);
            node.name = successor.name;
            node.phone = successor.phone;
            node.email = successor.email;

            // Delete successor from right subtree
            node.right = deleteRec(node.right, successor.name);
        }
        return node;
    }

    private int count = 0;

    public void inorder() {
        if (root == null) {
            System.out.println("Directory is empty. No contacts to display.");
            return;
        }

        System.out.println("\n=======================================================");
        System.out.println("   CONTACT DIRECTORY (Alphabetical Order - In-order)   ");
        System.out.println("=======================================================");
        count = 1;
        inorderRec(root);
        System.out.println("=======================================================\n");
    }

    private void inorderRec(ContactNode node) {
        if (node != null) {
            inorderRec(node.left);
            System.out.println((count++) + ". " + node);
            inorderRec(node.right);
        }
    }

    public static void main(String[] args) {
        ContactDirectoryBST directory = new ContactDirectoryBST();
        Scanner scanner = new Scanner(System.in);

        System.out.println("=======================================================");
        System.out.println("    CONTACT DIRECTORY (BST BASED) - Java               ");
        System.out.println("=======================================================");

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Insert / Update Contact");
            System.out.println("2. Search Contact");
            System.out.println("3. Delete Contact");
            System.out.println("4. Display All Contacts (In-order)");
            System.out.println("5. Exit");
            System.out.print("Enter choice (1-5): ");

            if (!scanner.hasNextLine()) break;
            String choice = scanner.nextLine().trim();

            if (choice.equals("5") || choice.equalsIgnoreCase("exit") || choice.equalsIgnoreCase("quit")) {
                System.out.println("Exiting Contact Directory. Goodbye!");
                break;
            }

            switch (choice) {
                case "1":
                    System.out.print("Enter Contact Name: ");
                    String name = scanner.nextLine().trim();
                    System.out.print("Enter Phone Number: ");
                    String phone = scanner.nextLine().trim();
                    System.out.print("Enter Email (Optional, press Enter to skip): ");
                    String email = scanner.nextLine().trim();
                    directory.insert(name, phone, email);
                    break;
                case "2":
                    System.out.print("Enter Name to Search: ");
                    String searchName = scanner.nextLine().trim();
                    ContactNode found = directory.search(searchName);
                    if (found != null) {
                        System.out.println("\n[Found Contact]\n" + found);
                    } else {
                        System.out.println("\nContact '" + searchName + "' not found.");
                    }
                    break;
                case "3":
                    System.out.print("Enter Name to Delete: ");
                    String delName = scanner.nextLine().trim();
                    if (directory.delete(delName)) {
                        System.out.println("-> Successfully deleted contact: '" + delName + "'");
                    } else {
                        System.out.println("-> Contact '" + delName + "' not found.");
                    }
                    break;
                case "4":
                    directory.inorder();
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                    break;
            }
        }

        scanner.close();
    }
}
