"""
Contact Directory (BST Based)
-----------------------------
Features:
- Insert Contact (Name, Phone Number, Email)
- Search Contact by Name (Case-insensitive)
- Delete Contact by Name (handles 0, 1, or 2 children in BST)
- In-order Traversal (alphabetical listing)
- Interactive CLI Menu
"""

class ContactNode:
    def __init__(self, name: str, phone: str, email: str = ""):
        self.name = name.strip()
        self.phone = phone.strip()
        self.email = email.strip()
        self.left = None
        self.right = None

    def __str__(self):
        email_str = f" | Email: {self.email}" if self.email else ""
        return f"Name: {self.name} | Phone: {self.phone}{email_str}"


class ContactDirectoryBST:
    def __init__(self):
        self.root = None

    def insert(self, name: str, phone: str, email: str = "") -> bool:
        """Inserts a contact into the BST. Updates if name already exists."""
        name = name.strip()
        phone = phone.strip()
        email = email.strip()

        if not name or not phone:
            print("Error: Name and phone cannot be empty.")
            return False

        if self.root is None:
            self.root = ContactNode(name, phone, email)
            print(f"-> Successfully added contact: '{name}'")
            return True

        curr = self.root
        while curr:
            # Case-insensitive comparison for alphabetical order
            if name.lower() == curr.name.lower():
                # Update existing contact
                curr.phone = phone
                curr.email = email
                print(f"-> Contact '{name}' already exists. Details updated!")
                return True
            elif name.lower() < curr.name.lower():
                if curr.left is None:
                    curr.left = ContactNode(name, phone, email)
                    print(f"-> Successfully added contact: '{name}'")
                    return True
                curr = curr.left
            else:
                if curr.right is None:
                    curr.right = ContactNode(name, phone, email)
                    print(f"-> Successfully added contact: '{name}'")
                    return True
                curr = curr.right
        return False

    def search(self, name: str) -> ContactNode:
        """Searches for a contact by name (case-insensitive)."""
        target = name.strip().lower()
        curr = self.root
        while curr:
            curr_name = curr.name.lower()
            if target == curr_name:
                return curr
            elif target < curr_name:
                curr = curr.left
            else:
                curr = curr.right
        return None

    def _min_value_node(self, node: ContactNode) -> ContactNode:
        """Helper to find the in-order successor (smallest node in the right subtree)."""
        current = node
        while current.left is not None:
            current = current.left
        return current

    def delete(self, name: str) -> bool:
        """Deletes contact by name."""
        deleted = [False]

        def _delete_rec(node: ContactNode, key: str):
            if node is None:
                return None

            key_lower = key.lower()
            curr_lower = node.name.lower()

            if key_lower < curr_lower:
                node.left = _delete_rec(node.left, key)
            elif key_lower > curr_lower:
                node.right = _delete_rec(node.right, key)
            else:
                # Node to be deleted found
                deleted[0] = True

                # Case 1 & 2: Node with 0 or 1 child
                if node.left is None:
                    return node.right
                elif node.right is None:
                    return node.left

                # Case 3: Node with 2 children
                # Get the inorder successor (smallest in the right subtree)
                successor = self._min_value_node(node.right)
                node.name = successor.name
                node.phone = successor.phone
                node.email = successor.email

                # Delete the inorder successor
                node.right = _delete_rec(node.right, successor.name)

            return node

        self.root = _delete_rec(self.root, name)
        return deleted[0]

    def inorder(self) -> None:
        """Performs in-order traversal to print contacts alphabetically."""
        contacts = []

        def _inorder_rec(node):
            if node:
                _inorder_rec(node.left)
                contacts.append(node)
                _inorder_rec(node.right)

        _inorder_rec(self.root)

        if not contacts:
            print("Directory is empty. No contacts to display.")
            return

        print("\n" + "=" * 55)
        print(f"   CONTACT DIRECTORY (Alphabetical Order - {len(contacts)} contacts)")
        print("=" * 55)
        for idx, contact in enumerate(contacts, 1):
            print(f"{idx}. {contact}")
        print("=" * 55 + "\n")


def interactive_cli():
    directory = ContactDirectoryBST()
    print("=" * 55)
    print("    CONTACT DIRECTORY (BST BASED) - Python      ")
    print("=" * 55)

    while True:
        print("\nMenu:")
        print("1. Insert / Update Contact")
        print("2. Search Contact")
        print("3. Delete Contact")
        print("4. Display All Contacts (In-order / Alphabetical)")
        print("5. Exit")

        try:
            choice = input("Enter choice (1-5): ").strip()
            if choice == "1":
                name = input("Enter Contact Name: ").strip()
                phone = input("Enter Phone Number: ").strip()
                email = input("Enter Email (Optional, press Enter to skip): ").strip()
                directory.insert(name, phone, email)
            elif choice == "2":
                name = input("Enter Name to Search: ").strip()
                result = directory.search(name)
                if result:
                    print(f"\n[Found Contact]\n{result}")
                else:
                    print(f"\nContact '{name}' not found.")
            elif choice == "3":
                name = input("Enter Name to Delete: ").strip()
                if directory.delete(name):
                    print(f"-> Successfully deleted contact: '{name}'")
                else:
                    print(f"-> Contact '{name}' not found.")
            elif choice == "4":
                directory.inorder()
            elif choice in ("5", "exit", "quit"):
                print("Exiting Contact Directory. Goodbye!")
                break
            else:
                print("Invalid choice. Please enter a number between 1 and 5.")
        except (KeyboardInterrupt, EOFError):
            print("\nExiting Contact Directory.")
            break

if __name__ == "__main__":
    interactive_cli()
