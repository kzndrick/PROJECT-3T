package inventory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class InventoryManagementSystem extends JFrame {
    private JTextField idField, nameField, quantityField;
    private JTextArea inventoryArea;
    private List<InventoryItem> inventory;

    public InventoryManagementSystem() {
        inventory = FileIO.readInventory();
        createUI();
    }

    private void createUI() {
        setTitle("Inventory Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create Menu Bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        // Create Toolbar
        JToolBar toolBar = new JToolBar();
        JButton addButton = new JButton("Add");
        addButton.addActionListener(new AddItemAction());
        toolBar.add(addButton);

        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(new UpdateItemAction());
        toolBar.add(updateButton);

        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(new RemoveItemAction());
        toolBar.add(removeButton);

        add(toolBar, BorderLayout.NORTH);

        // Create Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Create Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Item Details"));

        inputPanel.add(new JLabel("ID:"));
        idField = new JTextField();
        inputPanel.add(idField);

        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        inputPanel.add(quantityField);

        mainPanel.add(inputPanel, BorderLayout.NORTH);

        // Create Inventory Display Area
        inventoryArea = new JTextArea();
        inventoryArea.setEditable(false);
        inventoryArea.setBorder(BorderFactory.createTitledBorder("Inventory"));
        JScrollPane scrollPane = new JScrollPane(inventoryArea);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);

        updateInventoryArea();
    }

    private void updateInventoryArea() {
        inventoryArea.setText("");
        for (InventoryItem item : inventory) {
            inventoryArea.append(item + "\n");
        }
    }

    private class AddItemAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String id = idField.getText();
            String name = nameField.getText();
            int quantity = Integer.parseInt(quantityField.getText());
            inventory.add(new InventoryItem(id, name, quantity));
            FileIO.writeInventory(inventory);
            updateInventoryArea();
        }
    }

    private class UpdateItemAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String id = idField.getText();
            for (InventoryItem item : inventory) {
                if (item.getId().equals(id)) {
                    item.setName(nameField.getText());
                    item.setQuantity(Integer.parseInt(quantityField.getText()));
                    break;
                }
            }
            FileIO.writeInventory(inventory);
            updateInventoryArea();
        }
    }

    private class RemoveItemAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String id = idField.getText();
            inventory.removeIf(item -> item.getId().equals(id));
            FileIO.writeInventory(inventory);
            updateInventoryArea();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InventoryManagementSystem ims = new InventoryManagementSystem();
            ims.setVisible(true);
        });
    }
}
