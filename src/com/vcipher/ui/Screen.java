package com.vcipher.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import javax.swing.Box;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.JTextComponent;

import com.vcipher.io.ServiceManager;

public class Screen extends JFrame {
    private final ThemeConfig theme;
    private final CardLayout pageLayout = new CardLayout();
    private final JPanel pages = new JPanel(pageLayout);
    private final JLabel statusLabel = new JLabel("Ready to seal a message.");
    private final JLabel encodeImageName = new JLabel("No cover image selected");
    private final JLabel decodeImageName = new JLabel("No image selected");
    private final JLabel capacityLabel = new JLabel("Choose an image to check its capacity.");
    private final JLabel attachmentLabel = new JLabel("No attachment");
    private final JLabel encodePreview = new JLabel("IMAGE PREVIEW", SwingConstants.CENTER);
    private final JLabel decodePreview = new JLabel("IMAGE PREVIEW", SwingConstants.CENTER);
    private final JTextArea messageInput;
    private final JTextArea messageOutput;
    private final JPasswordField encodePassword = new JPasswordField();
    private final JPasswordField decodePassword = new JPasswordField();
    private final ServiceManager serviceManager = new ServiceManager();

    private BufferedImage encodeImage;
    private BufferedImage decodeImage;
    private String attachmentText = "";

    public Screen() {
        this(ThemeConfig.defaultConfig());
    }

    public Screen(ThemeConfig theme) {
        super("The Vishanti Cipher");
        this.theme = Objects.requireNonNull(theme, "Theme config cannot be null.");
        messageInput = createTextArea("Write your secret message here...");
        messageOutput = createTextArea("");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(theme.windowMinWidth, theme.windowMinHeight));
        setSize(theme.windowWidth, theme.windowHeight);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(theme.background);
        pages.setOpaque(false);
        root.add(createHeader(), BorderLayout.NORTH);
        root.add(createWorkspace(), BorderLayout.CENTER);
        root.add(createStatusBar(), BorderLayout.SOUTH);
        setContentPane(root);

        pages.add(createEncodePage(), "encode");
        pages.add(createDecodePage(), "decode");
        pageLayout.show(pages, "encode");
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout(theme.headerGap, 0));
        header.setBackground(theme.ink);
        header.setBorder(theme.headerBorder());

        JPanel brand = new JPanel(new BorderLayout(theme.brandGap, 0));
        brand.setOpaque(false);
        JLabel mark = new JLabel("VC", SwingConstants.CENTER);
        mark.setOpaque(true);
        mark.setBackground(theme.brandAccent);
        mark.setForeground(theme.surface);
        mark.setFont(theme.boldFont(theme.fontSizeMark));
        mark.setPreferredSize(new Dimension(theme.brandMarkSize, theme.brandMarkSize));
        brand.add(mark, BorderLayout.WEST);

        JPanel brandText = new JPanel(new GridBagLayout());
        brandText.setOpaque(false);
        GridBagConstraints textConstraints = new GridBagConstraints();
        textConstraints.gridx = 0;
        textConstraints.anchor = GridBagConstraints.WEST;
        textConstraints.insets = theme.spacingInsets(0, 0, theme.headerTitleBaselineGap, 0);
        JLabel title = new JLabel("The Vishanti Cipher");
        title.setFont(theme.boldFont(theme.fontSizeTitle));
        title.setForeground(theme.surface);
        brandText.add(title, textConstraints);
        textConstraints.gridy = 1;
        textConstraints.insets = theme.spacingInsets(0, 0, 0, 0);
        JLabel subtitle = new JLabel("A quiet place for your private messages");
        subtitle.setFont(theme.plainFont(theme.fontSizeBody));
        subtitle.setForeground(theme.headerSubtitle);
        brandText.add(subtitle, textConstraints);
        brand.add(brandText, BorderLayout.CENTER);
        header.add(brand, BorderLayout.WEST);

        JLabel badge = new JLabel("PRIVATE BY DESIGN");
        badge.setForeground(theme.headerBadge);
        badge.setFont(theme.boldFont(theme.fontSizeSmall));
        header.add(badge, BorderLayout.EAST);
        return header;
    }

    private JPanel createWorkspace() {
        JPanel workspace = new JPanel(new BorderLayout(0, theme.workspaceGap));
        workspace.setOpaque(false);
        workspace.setBorder(theme.workspaceBorder());

        JPanel tabs = new JPanel(new FlowLayout(FlowLayout.LEFT, theme.tabGap, 0));
        tabs.setOpaque(false);
        SquircleButton encodeTab = new SquircleButton("Seal message", false);
        SquircleButton decodeTab = new SquircleButton("Reveal message", false);
        encodeTab.setActive(true);
        encodeTab.addActionListener(event -> {
            encodeTab.setActive(true);
            decodeTab.setActive(false);
            pageLayout.show(pages, "encode");
            statusLabel.setText("Ready to seal a message.");
        });
        decodeTab.addActionListener(event -> {
            decodeTab.setActive(true);
            encodeTab.setActive(false);
            pageLayout.show(pages, "decode");
            statusLabel.setText("Ready to reveal a message.");
        });
        tabs.add(encodeTab);
        tabs.add(decodeTab);
        workspace.add(tabs, BorderLayout.NORTH);
        JScrollPane pageScroll = new JScrollPane(pages);
        pageScroll.setBorder(theme.pageBorder());
        pageScroll.setOpaque(false);
        pageScroll.getViewport().setOpaque(false);
        pageScroll.getVerticalScrollBar().setUnitIncrement(theme.scrollUnitIncrement);
        workspace.add(pageScroll, BorderLayout.CENTER);
        return workspace;
    }

    private JPanel createEncodePage() {
        JPanel page = createPagePanel();
        page.add(createImageCard(true));
        page.add(Box.createVerticalStrut(theme.cardGap));

        SquirclePanel messageCard = new SquirclePanel(new BorderLayout(0, theme.cardGap), theme.surface);
        messageCard.setBorder(theme.messageCardBorder());
        messageCard.add(createCardHeading("Your message", "Write a note or attach a text file."), BorderLayout.NORTH);
        JScrollPane messageScroll = new JScrollPane(messageInput);
        styleScrollPane(messageScroll, theme.messageAreaHeight);
        messageCard.add(messageScroll, BorderLayout.CENTER);

        JPanel attachmentRow = new JPanel(new BorderLayout(theme.cardGap, 0));
        attachmentRow.setOpaque(false);
        SquircleButton attachButton = new SquircleButton("+  Attach text file", false);
        attachButton.addActionListener(this::chooseAttachment);
        attachmentRow.add(attachButton, BorderLayout.WEST);
        attachmentLabel.setForeground(theme.muted);
        attachmentLabel.setFont(theme.plainFont(theme.fontSizeBody));
        attachmentRow.add(attachmentLabel, BorderLayout.CENTER);
        messageCard.add(attachmentRow, BorderLayout.SOUTH);
        page.add(messageCard);
        page.add(Box.createVerticalStrut(theme.cardGap));

        SquirclePanel passwordCard = createPasswordCard("Secret password", encodePassword);
        page.add(passwordCard);
        page.add(Box.createVerticalStrut(theme.cardGap));

        JPanel actionRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        actionRow.setOpaque(false);
        SquircleButton sealButton = new SquircleButton("Seal & save image", true);
        sealButton.addActionListener(this::sealMessage);
        actionRow.add(sealButton);
        page.add(actionRow);
        return page;
    }

    private JPanel createDecodePage() {
        JPanel page = createPagePanel();
        page.add(createImageCard(false));
        page.add(Box.createVerticalStrut(theme.cardGap));
        page.add(createPasswordCard("Secret password", decodePassword));
        page.add(Box.createVerticalStrut(theme.cardGap));

        JPanel actionRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        actionRow.setOpaque(false);
        SquircleButton revealButton = new SquircleButton("Reveal the message", true);
        revealButton.addActionListener(this::revealMessage);
        actionRow.add(revealButton);
        page.add(actionRow);
        page.add(Box.createVerticalStrut(theme.cardGap));

        SquirclePanel resultCard = new SquirclePanel(new BorderLayout(0, theme.cardGap), theme.surface);
        resultCard.setBorder(theme.resultCardBorder());
        resultCard.add(createCardHeading("Revealed message", "Your decrypted message will appear here."), BorderLayout.NORTH);
        JScrollPane resultScroll = new JScrollPane(messageOutput);
        styleScrollPane(resultScroll, theme.resultAreaHeight);
        resultCard.add(resultScroll, BorderLayout.CENTER);

        JPanel resultActions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        resultActions.setOpaque(false);
        SquircleButton saveMessageButton = new SquircleButton("Save message...", false);
        saveMessageButton.addActionListener(this::saveRevealedMessage);
        resultActions.add(saveMessageButton);
        resultCard.add(resultActions, BorderLayout.SOUTH);
        page.add(resultCard);
        return page;
    }

    private JPanel createPagePanel() {
        JPanel page = new JPanel();
        page.setOpaque(false);
        page.setLayout(new javax.swing.BoxLayout(page, javax.swing.BoxLayout.Y_AXIS));
        page.setBorder(theme.pageBorder());
        return page;
    }

    private SquirclePanel createImageCard(boolean forEncoding) {
        SquirclePanel card = new SquirclePanel(new BorderLayout(theme.imageCardGap, 0), theme.surface);
        card.setBorder(theme.imageCardBorder());

        JPanel details = new JPanel(new GridBagLayout());
        details.setOpaque(false);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        constraints.insets = theme.spacingInsets(0, 0, theme.gridHeadingBottomGap, 0);
        JLabel heading = new JLabel(forEncoding ? "1. Choose a cover image" : "1. Choose an image to reveal");
        heading.setFont(theme.boldFont(theme.fontSizeHeading));
        heading.setForeground(theme.ink);
        details.add(heading, constraints);

        constraints.gridy++;
        constraints.insets = theme.spacingInsets(0, 0, theme.gridFilenameBottomGap, 0);
        JLabel filename = forEncoding ? encodeImageName : decodeImageName;
        filename.setFont(theme.plainFont(theme.fontSizeBody));
        filename.setForeground(theme.muted);
        details.add(filename, constraints);

        constraints.gridy++;
        constraints.insets = theme.spacingInsets(0, 0, 0, 0);
        SquircleButton chooseButton = new SquircleButton("Choose image...", false);
        chooseButton.addActionListener(event -> chooseImage(forEncoding));
        details.add(chooseButton, constraints);

        if (forEncoding) {
            constraints.gridy++;
            constraints.insets = theme.spacingInsets(theme.capacityTopGap, 0, 0, 0);
            capacityLabel.setFont(theme.plainFont(theme.fontSizeBody));
            capacityLabel.setForeground(theme.muted);
            details.add(capacityLabel, constraints);
        }

        JLabel preview = forEncoding ? encodePreview : decodePreview;
        preview.setOpaque(true);
        preview.setBackground(theme.background);
        preview.setForeground(theme.muted);
        preview.setFont(theme.boldFont(theme.fontSizeSmall));
        preview.setPreferredSize(new Dimension(theme.previewWidth, theme.previewHeight));
        preview.setBorder(theme.previewBorder());

        card.add(details, BorderLayout.CENTER);
        card.add(preview, BorderLayout.EAST);
        return card;
    }

    private SquirclePanel createPasswordCard(String heading, JPasswordField passwordField) {
        SquirclePanel card = new SquirclePanel(new BorderLayout(0, theme.passwordCardGap), theme.surface);
        card.setBorder(theme.passwordCardBorder());
        card.add(createCardHeading("2. " + heading, "Use the same password when revealing the message."), BorderLayout.NORTH);
        styleTextField(passwordField);
        card.add(passwordField, BorderLayout.CENTER);
        return card;
    }

    private JPanel createCardHeading(String title, String subtitle) {
        JPanel heading = new JPanel(new BorderLayout(0, theme.headingGap));
        heading.setOpaque(false);
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(theme.boldFont(theme.fontSizeHeading));
        titleLabel.setForeground(theme.ink);
        JLabel subtitleLabel = new JLabel(subtitle);
        subtitleLabel.setFont(theme.plainFont(theme.fontSizeBody));
        subtitleLabel.setForeground(theme.muted);
        heading.add(titleLabel, BorderLayout.NORTH);
        heading.add(subtitleLabel, BorderLayout.SOUTH);
        return heading;
    }

    private JPanel createStatusBar() {
        JPanel statusBar = new JPanel(new BorderLayout());
        statusBar.setBackground(theme.surface);
        statusBar.setBorder(theme.statusBarBorder());
        statusLabel.setForeground(theme.muted);
        statusLabel.setFont(theme.plainFont(theme.fontSizeBody));
        statusBar.add(statusLabel, BorderLayout.WEST);
        JLabel footer = new JLabel("V-CIPHER");
        footer.setForeground(theme.footer);
        footer.setFont(theme.boldFont(theme.fontSizeSmall));
        statusBar.add(footer, BorderLayout.EAST);
        return statusBar;
    }

    private void chooseImage(boolean forEncoding) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Image files",
                new String[] { "png", "jpg", "jpeg", "gif", "bmp" }));
        if (chooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File selectedFile = chooser.getSelectedFile();
        try {
            BufferedImage image = serviceManager.loadImage(selectedFile);
            if (forEncoding) {
                encodeImage = image;
                encodeImageName.setText(selectedFile.getName());
                updatePreview(encodePreview, image);
                updateCapacity(image);
                statusLabel.setText("Cover image ready.");
            } else {
                decodeImage = image;
                decodeImageName.setText(selectedFile.getName());
                updatePreview(decodePreview, image);
                statusLabel.setText("Image ready to reveal.");
            }
        } catch (IOException exception) {
            showError("Could not open image", exception);
        }
    }

    private void chooseAttachment(ActionEvent event) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Text files", "txt"));
        if (chooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File file = chooser.getSelectedFile();
        try {
            attachmentText = serviceManager.loadMessage(file);
            attachmentLabel.setText(file.getName());
            statusLabel.setText("Text attachment added.");
        } catch (IOException exception) {
            showError("Could not read attachment", exception);
        }
    }

    private void sealMessage(ActionEvent event) {
        if (encodeImage == null) {
            showError("Choose a cover image", "Select an image before sealing your message.");
            return;
        }
        String password = new String(encodePassword.getPassword());
        if (password.isEmpty()) {
            showError("Password required", "Enter a password to protect your message.");
            return;
        }

        String message = messageInput.getText();
        if (!attachmentText.isEmpty()) {
            message = message.isEmpty() ? attachmentText : message + System.lineSeparator() + attachmentText;
        }
        if (message.trim().isEmpty()) {
            showError("Message required", "Write a message or attach a text file before sealing.");
            return;
        }

        try {
            BufferedImage encodedImage = serviceManager.sealMessage(message, password, encodeImage);
            saveEncodedImage(encodedImage);
        } catch (ServiceManager.ImageCapacityExceededException exception) {
            showError("Image capacity exceeded", "Choose a larger image or use a shorter message.");
        } catch (Exception exception) {
            showError("Could not seal message", exception);
        }
    }

    private void saveEncodedImage(BufferedImage image) throws IOException {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Save sealed image");
        chooser.setSelectedFile(new File("sealed-image.png"));
        chooser.setFileFilter(new FileNameExtensionFilter("PNG image", "png"));
        if (chooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
            statusLabel.setText("Save cancelled.");
            return;
        }

        File file = chooser.getSelectedFile();
        if (!file.getName().toLowerCase(java.util.Locale.ROOT).endsWith(".png")) {
            file = new File(file.getParentFile(), file.getName() + ".png");
        }
        serviceManager.saveImage(image, file, "png");
        statusLabel.setText("Sealed image saved to " + file.getName() + ".");
    }

    private void saveRevealedMessage(ActionEvent event) {
        String message = messageOutput.getText();
        if (message.isEmpty()) {
            showError("No message to save", "Reveal a message before saving it.");
            return;
        }

        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Save revealed message");
        chooser.setSelectedFile(new File("revealed-message.txt"));
        chooser.setFileFilter(new FileNameExtensionFilter("Text files", "txt"));
        if (chooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
            statusLabel.setText("Save cancelled.");
            return;
        }

        File file = chooser.getSelectedFile();
        if (!file.getName().toLowerCase(java.util.Locale.ROOT).endsWith(".txt")) {
            file = new File(file.getParentFile(), file.getName() + ".txt");
        }
        try {
            serviceManager.saveMessage(message, file);
            statusLabel.setText("Message saved to " + file.getName() + ".");
        } catch (IOException exception) {
            showError("Could not save message", exception);
        }
    }

    private void revealMessage(ActionEvent event) {
        if (decodeImage == null) {
            showError("Choose an image", "Select the image containing the hidden message.");
            return;
        }
        String password = new String(decodePassword.getPassword());
        if (password.isEmpty()) {
            showError("Password required", "Enter the password used to seal the message.");
            return;
        }

        try {
            String message = serviceManager.revealMessage(decodeImage, password);
            messageOutput.setText(message);
            messageOutput.setCaretPosition(0);
            statusLabel.setText("Message revealed successfully.");
        } catch (Exception exception) {
            messageOutput.setText("");
            showError("Image has no message", "The selected image has no message.");
        }
    }

    private void updateCapacity(BufferedImage image) {
        long capacity = serviceManager.getMessageCapacityBytes(image);
        capacityLabel.setText("Approx. payload capacity: " + capacity + " bytes");
    }

    private void updatePreview(JLabel preview, BufferedImage image) {
        int width = preview.getWidth() > 0 ? preview.getWidth() : theme.previewWidth;
        int height = preview.getHeight() > 0 ? preview.getHeight() : theme.previewHeight;
        double scale = Math.min((double) width / image.getWidth(), (double) height / image.getHeight());
        int scaledWidth = Math.max(1, (int) (image.getWidth() * scale));
        int scaledHeight = Math.max(1, (int) (image.getHeight() * scale));
        preview.setText("");
        preview.setIcon(new javax.swing.ImageIcon(
                image.getScaledInstance(scaledWidth, scaledHeight, java.awt.Image.SCALE_SMOOTH)));
    }

    private JTextArea createTextArea(String initialText) {
        JTextArea textArea = new HintTextArea(initialText);
        textArea.setFont(theme.plainFont(theme.fontSizeInput));
        textArea.setForeground(theme.ink);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBackground(theme.surface);
        textArea.setBorder(theme.textAreaBorder());
        return textArea;
    }

    private class HintTextArea extends JTextArea {
        private final String hint;

        HintTextArea(String hint) {
            this.hint = hint;
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            if (!hint.isEmpty() && getText().isEmpty() && !hasFocus()) {
                graphics.setColor(theme.muted);
                graphics.setFont(getFont());
                graphics.drawString(hint, getInsets().left + theme.hintTextLeftOffset,
                        getInsets().top + graphics.getFontMetrics().getAscent());
            }
        }
    }

    private void styleScrollPane(JScrollPane scrollPane, int height) {
        scrollPane.setPreferredSize(new Dimension(theme.contentWidth, height));
        scrollPane.setBorder(theme.scrollBorder());
        scrollPane.getViewport().setBackground(theme.surface);
    }

    private void styleTextField(JTextComponent field) {
        field.setFont(theme.plainFont(theme.fontSizeInput));
        field.setForeground(theme.ink);
        field.setBackground(theme.surface);
        field.setBorder(theme.textFieldBorder());
    }

    private void showError(String title, Exception exception) {
        showError(title, exception.getMessage() == null ? exception.toString() : exception.getMessage());
    }

    private void showError(String title, String message) {
        statusLabel.setText(title + ".");
        javax.swing.JOptionPane pane = new javax.swing.JOptionPane(
                message, javax.swing.JOptionPane.ERROR_MESSAGE, javax.swing.JOptionPane.DEFAULT_OPTION);
        javax.swing.JDialog dialog = pane.createDialog(this, title);
        dialog.setDefaultCloseOperation(javax.swing.JDialog.DISPOSE_ON_CLOSE);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private class SquirclePanel extends JPanel {
        private final Color fill;

        SquirclePanel(java.awt.LayoutManager layout, Color fill) {
            super(layout);
            this.fill = fill;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            theme.paintSquircle(graphics, getWidth(), getHeight(), fill, theme.border, theme.cardRadius);
            super.paintComponent(graphics);
        }
    }

    private class SquircleButton extends JButton {
        private final boolean primary;
        private boolean active;

        SquircleButton(String text, boolean primary) {
            super(text);
            this.primary = primary;
            setFont(theme.boldFont(theme.fontSizeButton));
            setForeground(primary ? theme.surface : theme.ink);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setOpaque(false);
            setMargin(theme.spacingInsets(theme.buttonPaddingVertical, theme.buttonPaddingHorizontal,
                    theme.buttonPaddingVertical, theme.buttonPaddingHorizontal));
        }

        void setActive(boolean active) {
            this.active = active;
            if (!primary) {
                setForeground(active ? theme.accent : theme.ink);
            }
            repaint();
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            Color fill = theme.buttonBackground(primary, active, getModel().isPressed(), getModel().isRollover());
            theme.paintSquircle(graphics, getWidth(), getHeight(), fill,
                    primary ? theme.accent : theme.border, theme.buttonRadius);
            super.paintComponent(graphics);
        }
    }
}
