# Contact Directory (BST Based)

A mini project implementing a **Contact Directory** powered by a **Binary Search Tree (BST)** data structure.

## 📌 Features
- **Insert**: Adds a contact `(Name, Phone, Email)` to the BST based on alphabetical ordering (case-insensitive). Updates contact if the name already exists.
- **Search**: Performs BST search for a contact name with average $O(\log n)$ time complexity.
- **Delete**: Removes a contact from the BST correctly handling:
  - Leaf nodes (0 children).
  - Nodes with 1 child.
  - Nodes with 2 children (replaces with in-order successor).
- **Display Alphabetically (`inorder()`)**: Performs an in-order tree traversal ($Left \to Root \to Right$) to print all contacts sorted in alphabetical order.
- Implementations provided in both **Python** and **Java**.

---

## 🚀 How to Run

### 1. Python (`contact_directory.py`)
```bash
python contact_directory.py
```

### 2. Java (`ContactDirectoryBST.java`)
```bash
javac ContactDirectoryBST.java
java ContactDirectoryBST
```

---

## 💻 Sample Interaction
```text
=======================================================
    CONTACT DIRECTORY (BST BASED)
=======================================================

Menu:
1. Insert / Update Contact
2. Search Contact
3. Delete Contact
4. Display All Contacts (In-order / Alphabetical)
5. Exit

Enter choice (1-5): 1
Enter Contact Name: John Doe
Enter Phone Number: +1-555-0199
Enter Email (Optional): john@example.com
-> Successfully added contact: 'John Doe'

Enter choice (1-5): 4
=======================================================
   CONTACT DIRECTORY (Alphabetical Order - 3 contacts)
=======================================================
1. Name: Alice Smith | Phone: +1-555-0101 | Email: alice@example.com
2. Name: John Doe | Phone: +1-555-0199 | Email: john@example.com
3. Name: Zach Taylor | Phone: +1-555-0245
=======================================================

Enter choice (1-5): 2
Enter Name to Search: John Doe
[Found Contact]
Name: John Doe | Phone: +1-555-0199 | Email: john@example.com

Enter choice (1-5): 3
Enter Name to Delete: John Doe
-> Successfully deleted contact: 'John Doe'
```
