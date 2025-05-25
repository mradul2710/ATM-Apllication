import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class ATMApplication extends JFrame {
    private static final Color PRIMARY_COLOR = new Color(25, 118, 210);
    private static final Color SECONDARY_COLOR = new Color(33, 150, 243);
    private static final Color SUCCESS_COLOR = new Color(76, 175, 80);
    private static final Color ERROR_COLOR = new Color(244, 67, 54);
    private static final Color BACKGROUND_COLOR = new Color(245, 245, 245);
    private static final Color CARD_COLOR = Color.WHITE;
    private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 28);
    private static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 16);
    private static final Font TEXT_FONT = new Font("Segoe UI", Font.PLAIN, 14);

    private CardLayout cardLayout;
    private JPanel mainPanel;
    private Account currentAccount;
    private Map<String, Account> accounts;

    // Panels
    private JPanel loginPanel, menuPanel, balancePanel, withdrawPanel,
            depositPanel, transferPanel, historyPanel, pinChangePanel;

    public ATMApplication() {
        initializeAccounts();
        setupUI();
        setVisible(true);
    }

    private void initializeAccounts() {
        accounts = new HashMap<>();
        accounts.put("1234", new Account("1234", "5000", 250000.0)); // ₹2,50,000
        accounts.put("5678", new Account("5678", "1234", 150000.0)); // ₹1,50,000
        accounts.put("9999", new Account("9999", "0000", 50000.0));  // ₹50,000
    }

    private void setupUI() {
        setTitle("SecureBank ATM");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setResizable(false);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(BACKGROUND_COLOR);

        createPanels();
        add(mainPanel);

        showPanel("login");
    }

    private void createPanels() {
        createLoginPanel();
        createMenuPanel();
        createBalancePanel();
        createWithdrawPanel();
        createDepositPanel();
        createTransferPanel();
        createHistoryPanel();
        createPinChangePanel();
    }

    private void createLoginPanel() {
        loginPanel = new JPanel(new BorderLayout());
        loginPanel.setBackground(BACKGROUND_COLOR);

        // Header
        JPanel headerPanel = createHeaderPanel("Welcome to SecureBank ATM", "Please insert your card and enter PIN");

        // Main content
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(BACKGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();

        // Card panel
        JPanel cardPanel = createCardPanel();
        cardPanel.setLayout(new GridBagLayout());
        GridBagConstraints cardGbc = new GridBagConstraints();

        // Card number field
        JLabel cardLabel = new JLabel("Card Number:");
        cardLabel.setFont(TEXT_FONT);
        cardLabel.setForeground(Color.DARK_GRAY);
        JTextField cardField = createStyledTextField();
        cardField.setText("1234"); // Demo purposes

        // PIN field
        JLabel pinLabel = new JLabel("PIN:");
        pinLabel.setFont(TEXT_FONT);
        pinLabel.setForeground(Color.DARK_GRAY);
        JPasswordField pinField = createStyledPasswordField();
        pinField.setText("5000"); // Demo purposes

        // Login button
        JButton loginButton = createStyledButton("LOGIN", PRIMARY_COLOR);
        loginButton.addActionListener(e -> handleLogin(cardField.getText(), new String(pinField.getPassword())));

        // Demo info panel
        JPanel demoPanel = createDemoInfoPanel();

        // Layout card panel
        cardGbc.insets = new Insets(10, 20, 10, 20);
        cardGbc.fill = GridBagConstraints.HORIZONTAL;

        cardGbc.gridx = 0; cardGbc.gridy = 0;
        cardPanel.add(cardLabel, cardGbc);
        cardGbc.gridy = 1;
        cardPanel.add(cardField, cardGbc);
        cardGbc.gridy = 2;
        cardPanel.add(pinLabel, cardGbc);
        cardGbc.gridy = 3;
        cardPanel.add(pinField, cardGbc);
        cardGbc.gridy = 4;
        cardGbc.insets = new Insets(20, 20, 10, 20);
        cardPanel.add(loginButton, cardGbc);

        // Layout main content
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.insets = new Insets(20, 0, 20, 0);
        contentPanel.add(cardPanel, gbc);
        gbc.gridy = 1;
        contentPanel.add(demoPanel, gbc);

        loginPanel.add(headerPanel, BorderLayout.NORTH);
        loginPanel.add(contentPanel, BorderLayout.CENTER);

        mainPanel.add(loginPanel, "login");
    }

    private JPanel createDemoInfoPanel() {
        JPanel panel = createCardPanel();
        panel.setLayout(new GridLayout(0, 1, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR, 2),
                "Demo Accounts",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 12),
                PRIMARY_COLOR
        ));

        String[][] demoData = {
                {"Card: 1234", "PIN: 5000", "Balance: ₹2,50,000"},
                {"Card: 5678", "PIN: 1234", "Balance: ₹1,50,000"},
                {"Card: 9999", "PIN: 0000", "Balance: ₹50,000"}
        };

        for (String[] data : demoData) {
            JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            rowPanel.setBackground(Color.WHITE);
            for (String info : data) {
                JLabel label = new JLabel(info);
                label.setFont(new Font("Segoe UI", Font.PLAIN, 11));
                label.setForeground(Color.DARK_GRAY);
                rowPanel.add(label);
                rowPanel.add(Box.createHorizontalStrut(10));
            }
            panel.add(rowPanel);
        }

        return panel;
    }

    private void createMenuPanel() {
        menuPanel = new JPanel(new BorderLayout());
        menuPanel.setBackground(BACKGROUND_COLOR);

        // Header with user info
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setPreferredSize(new Dimension(0, 100));

        JLabel welcomeLabel = new JLabel("Welcome!", SwingConstants.CENTER);
        welcomeLabel.setFont(TITLE_FONT);
        welcomeLabel.setForeground(Color.WHITE);

        JButton logoutButton = createStyledButton("LOGOUT", ERROR_COLOR);
        logoutButton.setPreferredSize(new Dimension(100, 40));
        logoutButton.addActionListener(e -> showPanel("login"));

        headerPanel.add(welcomeLabel, BorderLayout.CENTER);
        headerPanel.add(logoutButton, BorderLayout.EAST);

        // Menu grid
        JPanel menuGrid = new JPanel(new GridLayout(2, 3, 20, 20));
        menuGrid.setBackground(BACKGROUND_COLOR);
        menuGrid.setBorder(new EmptyBorder(40, 40, 40, 40));

        // Menu buttons
        String[][] menuItems = {
                {"Balance Inquiry", "balance", "💰"},
                {"Withdraw Cash", "withdraw", "💸"},
                {"Deposit Cash", "deposit", "💵"},
                {"Transfer Funds", "transfer", "🔄"},
                {"Transaction History", "history", "📋"},
                {"Change PIN", "pinchange", "🔐"}
        };

        for (String[] item : menuItems) {
            JButton button = createMenuButton(item[0], item[2]);
            button.addActionListener(e -> showPanel(item[1]));
            menuGrid.add(button);
        }

        menuPanel.add(headerPanel, BorderLayout.NORTH);
        menuPanel.add(menuGrid, BorderLayout.CENTER);

        mainPanel.add(menuPanel, "menu");
    }

    private void createBalancePanel() {
        balancePanel = createTransactionPanel("Balance Inquiry", "💰");

        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel balanceLabel = new JLabel("Current Balance");
        balanceLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        balanceLabel.setForeground(Color.DARK_GRAY);

        JLabel amountLabel = new JLabel();
        amountLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        amountLabel.setForeground(SUCCESS_COLOR);

        JLabel dateLabel = new JLabel("As of: " + new SimpleDateFormat("MMM dd, yyyy HH:mm").format(new Date()));
        dateLabel.setFont(TEXT_FONT);
        dateLabel.setForeground(Color.GRAY);

        gbc.gridx = 0; gbc.gridy = 0;
        gbc.insets = new Insets(20, 0, 10, 0);
        contentPanel.add(balanceLabel, gbc);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 10, 0);
        contentPanel.add(amountLabel, gbc);
        gbc.gridy = 2;
        contentPanel.add(dateLabel, gbc);

        // Update balance when panel is shown
        balancePanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                if (currentAccount != null) {
                    amountLabel.setText(formatCurrency(currentAccount.getBalance()));
                }
            }
        });

        addContentToTransactionPanel(balancePanel, contentPanel);
        mainPanel.add(balancePanel, "balance");
    }

    private void createWithdrawPanel() {
        withdrawPanel = createTransactionPanel("Withdraw Cash", "💸");

        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();

        // Quick amount buttons
        JPanel quickAmountPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        quickAmountPanel.setBackground(Color.WHITE);

        int[] amounts = {500, 1000, 2000, 5000}; // Changed to Indian denominations
        for (int amount : amounts) {
            JButton btn = createStyledButton("₹" + amount, SECONDARY_COLOR);
            btn.addActionListener(e -> processWithdrawal(amount));
            quickAmountPanel.add(btn);
        }

        // Custom amount
        JLabel customLabel = new JLabel("Or enter custom amount:");
        customLabel.setFont(TEXT_FONT);
        JTextField customField = createStyledTextField();
        customField.setHorizontalAlignment(JTextField.CENTER);

        JButton withdrawButton = createStyledButton("WITHDRAW", PRIMARY_COLOR);
        withdrawButton.addActionListener(e -> {
            try {
                double amount = Double.parseDouble(customField.getText());
                processWithdrawal(amount);
                customField.setText("");
            } catch (NumberFormatException ex) {
                showMessage("Please enter a valid amount", ERROR_COLOR);
            }
        });

        gbc.gridx = 0; gbc.gridy = 0;
        gbc.insets = new Insets(20, 0, 20, 0);
        contentPanel.add(quickAmountPanel, gbc);
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 0, 5, 0);
        contentPanel.add(customLabel, gbc);
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 40, 10, 40);
        contentPanel.add(customField, gbc);
        gbc.gridy = 3;
        gbc.insets = new Insets(10, 40, 20, 40);
        contentPanel.add(withdrawButton, gbc);

        addContentToTransactionPanel(withdrawPanel, contentPanel);
        mainPanel.add(withdrawPanel, "withdraw");
    }

    private void createDepositPanel() {
        depositPanel = createTransactionPanel("Deposit Cash", "💵");

        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel instructionLabel = new JLabel("Enter the amount to deposit:");
        instructionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        instructionLabel.setForeground(Color.DARK_GRAY);

        JTextField amountField = createStyledTextField();
        amountField.setHorizontalAlignment(JTextField.CENTER);
        amountField.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JButton depositButton = createStyledButton("DEPOSIT", SUCCESS_COLOR);
        depositButton.addActionListener(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                processDeposit(amount);
                amountField.setText("");
            } catch (NumberFormatException ex) {
                showMessage("Please enter a valid amount", ERROR_COLOR);
            }
        });

        gbc.gridx = 0; gbc.gridy = 0;
        gbc.insets = new Insets(30, 0, 20, 0);
        contentPanel.add(instructionLabel, gbc);
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 60, 20, 60);
        contentPanel.add(amountField, gbc);
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 60, 30, 60);
        contentPanel.add(depositButton, gbc);

        addContentToTransactionPanel(depositPanel, contentPanel);
        mainPanel.add(depositPanel, "deposit");
    }

    private void createTransferPanel() {
        transferPanel = createTransactionPanel("Transfer Funds", "🔄");

        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel toLabel = new JLabel("To Account Number:");
        toLabel.setFont(TEXT_FONT);
        JTextField toField = createStyledTextField();

        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setFont(TEXT_FONT);
        JTextField amountField = createStyledTextField();

        JButton transferButton = createStyledButton("TRANSFER", PRIMARY_COLOR);
        transferButton.addActionListener(e -> {
            try {
                String toAccount = toField.getText();
                double amount = Double.parseDouble(amountField.getText());
                processTransfer(toAccount, amount);
                toField.setText("");
                amountField.setText("");
            } catch (NumberFormatException ex) {
                showMessage("Please enter a valid amount", ERROR_COLOR);
            }
        });

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 40, 5, 40);

        gbc.gridx = 0; gbc.gridy = 0;
        contentPanel.add(toLabel, gbc);
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 40, 15, 40);
        contentPanel.add(toField, gbc);
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 40, 5, 40);
        contentPanel.add(amountLabel, gbc);
        gbc.gridy = 3;
        gbc.insets = new Insets(5, 40, 20, 40);
        contentPanel.add(amountField, gbc);
        gbc.gridy = 4;
        gbc.insets = new Insets(10, 40, 20, 40);
        contentPanel.add(transferButton, gbc);

        addContentToTransactionPanel(transferPanel, contentPanel);
        mainPanel.add(transferPanel, "transfer");
    }

    private void createHistoryPanel() {
        historyPanel = createTransactionPanel("Transaction History", "📋");

        // Create table
        String[] columns = {"Date", "Type", "Amount", "Balance"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setFont(TEXT_FONT);
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(PRIMARY_COLOR);
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionBackground(new Color(230, 240, 255));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(600, 300));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Update table when panel is shown
        historyPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                if (currentAccount != null) {
                    model.setRowCount(0);
                    List<Transaction> history = currentAccount.getTransactionHistory();
                    for (int i = history.size() - 1; i >= 0; i--) {
                        Transaction t = history.get(i);
                        model.addRow(new Object[]{
                                new SimpleDateFormat("dd/MM/yyyy HH:mm").format(t.getDate()),
                                t.getType(),
                                formatCurrency(Math.abs(t.getAmount())),
                                formatCurrency(t.getBalance())
                        });
                    }
                }
            }
        });

        addContentToTransactionPanel(historyPanel, scrollPane);
        mainPanel.add(historyPanel, "history");
    }

    private void createPinChangePanel() {
        pinChangePanel = createTransactionPanel("Change PIN", "🔐");

        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel currentLabel = new JLabel("Current PIN:");
        currentLabel.setFont(TEXT_FONT);
        JPasswordField currentField = createStyledPasswordField();

        JLabel newLabel = new JLabel("New PIN:");
        newLabel.setFont(TEXT_FONT);
        JPasswordField newField = createStyledPasswordField();

        JLabel confirmLabel = new JLabel("Confirm New PIN:");
        confirmLabel.setFont(TEXT_FONT);
        JPasswordField confirmField = createStyledPasswordField();

        JButton changeButton = createStyledButton("CHANGE PIN", PRIMARY_COLOR);
        changeButton.addActionListener(e -> {
            String current = new String(currentField.getPassword());
            String newPin = new String(newField.getPassword());
            String confirm = new String(confirmField.getPassword());

            if (processPinChange(current, newPin, confirm)) {
                currentField.setText("");
                newField.setText("");
                confirmField.setText("");
            }
        });

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 40, 5, 40);

        gbc.gridx = 0; gbc.gridy = 0;
        contentPanel.add(currentLabel, gbc);
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 40, 15, 40);
        contentPanel.add(currentField, gbc);
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 40, 5, 40);
        contentPanel.add(newLabel, gbc);
        gbc.gridy = 3;
        gbc.insets = new Insets(5, 40, 15, 40);
        contentPanel.add(newField, gbc);
        gbc.gridy = 4;
        gbc.insets = new Insets(10, 40, 5, 40);
        contentPanel.add(confirmLabel, gbc);
        gbc.gridy = 5;
        gbc.insets = new Insets(5, 40, 20, 40);
        contentPanel.add(confirmField, gbc);
        gbc.gridy = 6;
        gbc.insets = new Insets(10, 40, 20, 40);
        contentPanel.add(changeButton, gbc);

        addContentToTransactionPanel(pinChangePanel, contentPanel);
        mainPanel.add(pinChangePanel, "pinchange");
    }

    // Helper methods for UI creation
    private JPanel createHeaderPanel(String title, String subtitle) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(PRIMARY_COLOR);
        panel.setPreferredSize(new Dimension(0, 120));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel(subtitle);
        subtitleLabel.setFont(TEXT_FONT);
        subtitleLabel.setForeground(new Color(200, 230, 255));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalGlue());
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(subtitleLabel);
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    private JPanel createCardPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(CARD_COLOR);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        return panel;
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField(15);
        field.setFont(TEXT_FONT);
        field.setPreferredSize(new Dimension(200, 40));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        return field;
    }

    private JPasswordField createStyledPasswordField() {
        JPasswordField field = new JPasswordField(15);
        field.setFont(TEXT_FONT);
        field.setPreferredSize(new Dimension(200, 40));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        return field;
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(200, 45));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effects
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(color.darker());
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }
        });

        return button;
    }

    private JButton createMenuButton(String text, String emoji) {
        JButton button = new JButton("<html><center>" + emoji + "<br><br>" + text + "</center></html>");
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(CARD_COLOR);
        button.setForeground(Color.DARK_GRAY);
        button.setPreferredSize(new Dimension(180, 120));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effects
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(245, 245, 245));
                button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(PRIMARY_COLOR, 2),
                        BorderFactory.createEmptyBorder(9, 9, 9, 9)
                ));
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(CARD_COLOR);
                button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                        BorderFactory.createEmptyBorder(10, 10, 10, 10)
                ));
            }
        });

        return button;
    }

    private JPanel createTransactionPanel(String title, String emoji) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setPreferredSize(new Dimension(0, 80));

        JLabel titleLabel = new JLabel(emoji + " " + title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);

        JButton backButton = createStyledButton("← BACK", new Color(100, 100, 100));
        backButton.setPreferredSize(new Dimension(100, 40));
        backButton.addActionListener(e -> showPanel("menu"));

        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(backButton, BorderLayout.WEST);

        panel.add(headerPanel, BorderLayout.NORTH);

        return panel;
    }

    private void addContentToTransactionPanel(JPanel panel, Component content) {
        JPanel contentWrapper = new JPanel(new GridBagLayout());
        contentWrapper.setBackground(BACKGROUND_COLOR);

        JPanel cardPanel = createCardPanel();
        cardPanel.setLayout(new BorderLayout());
        cardPanel.add(content, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(40, 40, 40, 40);
        contentWrapper.add(cardPanel, gbc);

        panel.add(contentWrapper, BorderLayout.CENTER);
    }

    // Business logic methods
    private String formatCurrency(double amount) {
        return String.format("₹%.2f", amount);
    }

    private void generateReceipt(String transactionType, double amount, String details) {
        JDialog receiptDialog = new JDialog(this, "Transaction Receipt", true);
        receiptDialog.setSize(450, 600);
        receiptDialog.setLocationRelativeTo(this);

        JPanel receiptPanel = new JPanel();
        receiptPanel.setLayout(new BoxLayout(receiptPanel, BoxLayout.Y_AXIS));
        receiptPanel.setBackground(Color.WHITE);
        receiptPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Bank header
        JLabel bankLabel = new JLabel("SECUREBANK ATM", SwingConstants.CENTER);
        bankLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        bankLabel.setForeground(PRIMARY_COLOR);
        bankLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel addressLabel = new JLabel("123 Banking Street, Mumbai - 400001", SwingConstants.CENTER);
        addressLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        addressLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Receipt details
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String currentDateTime = dateFormat.format(new Date());

        String receiptText = String.format(
                "<html><div style='font-family: monospace; font-size: 11px;'>" +
                        "<br>================================<br>" +
                        "<b>TRANSACTION RECEIPT</b><br>" +
                        "================================<br><br>" +
                        "Date/Time: %s<br>" +
                        "Card Number: ****%s<br>" +
                        "Transaction: %s<br>" +
                        "%s<br>" +
                        "Amount: %s<br>" +
                        "Available Balance: %s<br><br>" +
                        "================================<br>" +
                        "Thank you for using SecureBank!<br>" +
                        "Keep this receipt for your records<br>" +
                        "================================<br>" +
                        "</div></html>",
                currentDateTime,
                currentAccount.getCardNumber(),
                transactionType,
                details,
                formatCurrency(amount),
                formatCurrency(currentAccount.getBalance())
        );

        JLabel receiptLabel = new JLabel(receiptText);
        receiptLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.WHITE);

        JButton printButton = createStyledButton("PRINT", SUCCESS_COLOR);
        printButton.addActionListener(e -> {
            // Simulate printing
            showMessage("Receipt sent to printer successfully!", SUCCESS_COLOR);
        });

        JButton closeButton = createStyledButton("CLOSE", PRIMARY_COLOR);
        closeButton.addActionListener(e -> receiptDialog.dispose());

        buttonPanel.add(printButton);
        buttonPanel.add(closeButton);

        receiptPanel.add(bankLabel);
        receiptPanel.add(Box.createVerticalStrut(5));
        receiptPanel.add(addressLabel);
        receiptPanel.add(Box.createVerticalStrut(10));
        receiptPanel.add(receiptLabel);
        receiptPanel.add(Box.createVerticalStrut(20));
        receiptPanel.add(buttonPanel);

        receiptDialog.add(receiptPanel);
        receiptDialog.setVisible(true);
    }
    private void handleLogin(String cardNumber, String pin) {
        if (accounts.containsKey(cardNumber)) {
            Account account = accounts.get(cardNumber);
            if (account.getPin().equals(pin)) {
                currentAccount = account;
                showPanel("menu");
                showMessage("Welcome! Login successful.", SUCCESS_COLOR);
            } else {
                showMessage("Invalid PIN. Please try again.", ERROR_COLOR);
            }
        } else {
            showMessage("Card not recognized. Please try again.", ERROR_COLOR);
        }
    }

    private void processWithdrawal(double amount) {
        if (currentAccount.getBalance() >= amount && amount > 0) {
            currentAccount.withdraw(amount);
            showMessage(String.format("Successfully withdrew %s", formatCurrency(amount)), SUCCESS_COLOR);
            generateReceipt("CASH WITHDRAWAL", amount, "Cash dispensed successfully");
        } else if (amount <= 0) {
            showMessage("Please enter a valid amount.", ERROR_COLOR);
        } else {
            showMessage("Insufficient funds.", ERROR_COLOR);
        }
    }

    private void processDeposit(double amount) {
        if (amount > 0) {
            currentAccount.deposit(amount);
            showMessage(String.format("Successfully deposited %s", formatCurrency(amount)), SUCCESS_COLOR);
            generateReceipt("CASH DEPOSIT", amount, "Amount credited to account");
        } else {
            showMessage("Please enter a valid amount.", ERROR_COLOR);
        }
    }

    private void processTransfer(String toAccountNumber, double amount) {
        if (!accounts.containsKey(toAccountNumber)) {
            showMessage("Recipient account not found.", ERROR_COLOR);
            return;
        }

        if (toAccountNumber.equals(currentAccount.getCardNumber())) {
            showMessage("Cannot transfer to the same account.", ERROR_COLOR);
            return;
        }

        if (currentAccount.getBalance() >= amount && amount > 0) {
            Account toAccount = accounts.get(toAccountNumber);
            currentAccount.withdraw(amount);
            toAccount.deposit(amount);

            // Add transfer record to both accounts
            currentAccount.addTransaction("Transfer Out to " + toAccountNumber, -amount);
            toAccount.addTransaction("Transfer In from " + currentAccount.getCardNumber(), amount);

            showMessage(String.format("Successfully transferred %s to account %s", formatCurrency(amount), toAccountNumber), SUCCESS_COLOR);
            generateReceipt("FUND TRANSFER", amount, "Transfer to Account: " + toAccountNumber);
        } else if (amount <= 0) {
            showMessage("Please enter a valid amount.", ERROR_COLOR);
        } else {
            showMessage("Insufficient funds.", ERROR_COLOR);
        }
    }

    private boolean processPinChange(String currentPin, String newPin, String confirmPin) {
        if (!currentAccount.getPin().equals(currentPin)) {
            showMessage("Current PIN is incorrect.", ERROR_COLOR);
            return false;
        }

        if (newPin.length() != 4 || !newPin.matches("\\d+")) {
            showMessage("New PIN must be exactly 4 digits.", ERROR_COLOR);
            return false;
        }

        if (!newPin.equals(confirmPin)) {
            showMessage("New PIN and confirmation do not match.", ERROR_COLOR);
            return false;
        }

        currentAccount.setPin(newPin);
        showMessage("PIN successfully changed.", SUCCESS_COLOR);
        return true;
    }

    private void showPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
    }

    private void showMessage(String message, Color color) {
        JDialog dialog = new JDialog(this, "ATM Message", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(this);

        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel messageLabel = new JLabel("<html><center>" + message + "</center></html>");
        messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        messageLabel.setForeground(color);

        JButton okButton = createStyledButton("OK", PRIMARY_COLOR);
        okButton.addActionListener(e -> dialog.dispose());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 20, 0);
        contentPanel.add(messageLabel, gbc);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        contentPanel.add(okButton, gbc);

        dialog.add(contentPanel, BorderLayout.CENTER);
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ATMApplication();
        });
    }
}

// Account class to manage account data
class Account {
    private String cardNumber;
    private String pin;
    private double balance;
    private List<Transaction> transactionHistory;

    public Account(String cardNumber, String pin, double balance) {
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.balance = balance;
        this.transactionHistory = new ArrayList<>();

        // Add initial balance transaction
        addTransaction("Account Opening", balance);
    }

    public String getCardNumber() { return cardNumber; }
    public String getPin() { return pin; }
    public void setPin(String pin) { this.pin = pin; }
    public double getBalance() { return balance; }

    public void withdraw(double amount) {
        balance -= amount;
        addTransaction("Withdrawal", -amount);
    }

    public void deposit(double amount) {
        balance += amount;
        addTransaction("Deposit", amount);
    }

    public void addTransaction(String type, double amount) {
        transactionHistory.add(new Transaction(type, amount, balance));
    }

    public List<Transaction> getTransactionHistory() {
        return new ArrayList<>(transactionHistory);
    }
}

// Transaction class to store transaction details
class Transaction {
    private Date date;
    private String type;
    private double amount;
    private double balance;

    public Transaction(String type, double amount, double balance) {
        this.date = new Date();
        this.type = type;
        this.amount = amount;
        this.balance = balance;
    }

    public Date getDate() { return date; }
    public String getType() { return type; }
    public double getAmount() { return amount; }
    public double getBalance() { return balance; }
}