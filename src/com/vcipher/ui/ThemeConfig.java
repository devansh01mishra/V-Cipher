package com.vcipher.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.util.Objects;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

public final class ThemeConfig {
    public final Color background;
    public final Color surface;
    public final Color ink;
    public final Color muted;
    public final Color accent;
    public final Color border;
    public final Color brandAccent;
    public final Color headerSubtitle;
    public final Color headerBadge;
    public final Color footer;
    public final Color activeButton;
    public final Color pressedPrimaryButton;
    public final Color pressedButton;
    public final Color hoverPrimaryButton;
    public final Color hoverButton;
    public final String fontFamily;

    public final int headerPaddingTop;
    public final int headerPaddingLeft;
    public final int headerPaddingBottom;
    public final int headerPaddingRight;
    public final int workspacePaddingTop;
    public final int workspacePaddingLeft;
    public final int workspacePaddingBottom;
    public final int workspacePaddingRight;
    public final int messageCardPaddingTop;
    public final int messageCardPaddingLeft;
    public final int messageCardPaddingBottom;
    public final int messageCardPaddingRight;
    public final int resultCardPaddingTop;
    public final int resultCardPaddingLeft;
    public final int resultCardPaddingBottom;
    public final int resultCardPaddingRight;
    public final int imageCardPaddingTop;
    public final int imageCardPaddingLeft;
    public final int imageCardPaddingBottom;
    public final int imageCardPaddingRight;
    public final int passwordCardPaddingTop;
    public final int passwordCardPaddingLeft;
    public final int passwordCardPaddingBottom;
    public final int passwordCardPaddingRight;
    public final int pagePaddingTop;
    public final int pagePaddingLeft;
    public final int pagePaddingBottom;
    public final int pagePaddingRight;
    public final int textAreaPaddingTop;
    public final int textAreaPaddingLeft;
    public final int textAreaPaddingBottom;
    public final int textAreaPaddingRight;
    public final int textFieldPaddingTop;
    public final int textFieldPaddingLeft;
    public final int textFieldPaddingBottom;
    public final int textFieldPaddingRight;
    public final int statusBarPaddingTop;
    public final int statusBarPaddingLeft;
    public final int statusBarPaddingBottom;
    public final int statusBarPaddingRight;
    public final int statusBarDividerWidth;
    public final int borderThickness;

    public final int windowMinWidth;
    public final int windowMinHeight;
    public final int windowWidth;
    public final int windowHeight;
    public final int fontSizeSmall;
    public final int fontSizeBody;
    public final int fontSizeButton;
    public final int fontSizeInput;
    public final int fontSizeHeading;
    public final int fontSizeMark;
    public final int fontSizeTitle;
    public final int cardRadius;
    public final int buttonRadius;
    public final int previewWidth;
    public final int previewHeight;
    public final int messageAreaHeight;
    public final int resultAreaHeight;
    public final int scrollUnitIncrement;
    public final int headerGap;
    public final int brandGap;
    public final int workspaceGap;
    public final int tabGap;
    public final int cardGap;
    public final int imageCardGap;
    public final int headingGap;
    public final int passwordCardGap;
    public final int gridHeadingBottomGap;
    public final int gridFilenameBottomGap;
    public final int capacityTopGap;
    public final int brandMarkSize;
    public final int buttonPaddingVertical;
    public final int buttonPaddingHorizontal;
    public final int headerTitleBaselineGap;
    public final int contentWidth;
    public final int hintTextLeftOffset;

    private ThemeConfig(Builder builder) {
        background = builder.background;
        surface = builder.surface;
        ink = builder.ink;
        muted = builder.muted;
        accent = builder.accent;
        border = builder.border;
        brandAccent = builder.brandAccent;
        headerSubtitle = builder.headerSubtitle;
        headerBadge = builder.headerBadge;
        footer = builder.footer;
        activeButton = builder.activeButton;
        pressedPrimaryButton = builder.pressedPrimaryButton;
        pressedButton = builder.pressedButton;
        hoverPrimaryButton = builder.hoverPrimaryButton;
        hoverButton = builder.hoverButton;
        fontFamily = builder.fontFamily;

        headerPaddingTop = builder.headerPaddingTop;
        headerPaddingLeft = builder.headerPaddingLeft;
        headerPaddingBottom = builder.headerPaddingBottom;
        headerPaddingRight = builder.headerPaddingRight;
        workspacePaddingTop = builder.workspacePaddingTop;
        workspacePaddingLeft = builder.workspacePaddingLeft;
        workspacePaddingBottom = builder.workspacePaddingBottom;
        workspacePaddingRight = builder.workspacePaddingRight;
        messageCardPaddingTop = builder.messageCardPaddingTop;
        messageCardPaddingLeft = builder.messageCardPaddingLeft;
        messageCardPaddingBottom = builder.messageCardPaddingBottom;
        messageCardPaddingRight = builder.messageCardPaddingRight;
        resultCardPaddingTop = builder.resultCardPaddingTop;
        resultCardPaddingLeft = builder.resultCardPaddingLeft;
        resultCardPaddingBottom = builder.resultCardPaddingBottom;
        resultCardPaddingRight = builder.resultCardPaddingRight;
        imageCardPaddingTop = builder.imageCardPaddingTop;
        imageCardPaddingLeft = builder.imageCardPaddingLeft;
        imageCardPaddingBottom = builder.imageCardPaddingBottom;
        imageCardPaddingRight = builder.imageCardPaddingRight;
        passwordCardPaddingTop = builder.passwordCardPaddingTop;
        passwordCardPaddingLeft = builder.passwordCardPaddingLeft;
        passwordCardPaddingBottom = builder.passwordCardPaddingBottom;
        passwordCardPaddingRight = builder.passwordCardPaddingRight;
        pagePaddingTop = builder.pagePaddingTop;
        pagePaddingLeft = builder.pagePaddingLeft;
        pagePaddingBottom = builder.pagePaddingBottom;
        pagePaddingRight = builder.pagePaddingRight;
        textAreaPaddingTop = builder.textAreaPaddingTop;
        textAreaPaddingLeft = builder.textAreaPaddingLeft;
        textAreaPaddingBottom = builder.textAreaPaddingBottom;
        textAreaPaddingRight = builder.textAreaPaddingRight;
        textFieldPaddingTop = builder.textFieldPaddingTop;
        textFieldPaddingLeft = builder.textFieldPaddingLeft;
        textFieldPaddingBottom = builder.textFieldPaddingBottom;
        textFieldPaddingRight = builder.textFieldPaddingRight;
        statusBarPaddingTop = builder.statusBarPaddingTop;
        statusBarPaddingLeft = builder.statusBarPaddingLeft;
        statusBarPaddingBottom = builder.statusBarPaddingBottom;
        statusBarPaddingRight = builder.statusBarPaddingRight;
        statusBarDividerWidth = builder.statusBarDividerWidth;
        borderThickness = builder.borderThickness;

        windowMinWidth = builder.windowMinWidth;
        windowMinHeight = builder.windowMinHeight;
        windowWidth = builder.windowWidth;
        windowHeight = builder.windowHeight;
        fontSizeSmall = builder.fontSizeSmall;
        fontSizeBody = builder.fontSizeBody;
        fontSizeButton = builder.fontSizeButton;
        fontSizeInput = builder.fontSizeInput;
        fontSizeHeading = builder.fontSizeHeading;
        fontSizeMark = builder.fontSizeMark;
        fontSizeTitle = builder.fontSizeTitle;
        cardRadius = builder.cardRadius;
        buttonRadius = builder.buttonRadius;
        previewWidth = builder.previewWidth;
        previewHeight = builder.previewHeight;
        messageAreaHeight = builder.messageAreaHeight;
        resultAreaHeight = builder.resultAreaHeight;
        scrollUnitIncrement = builder.scrollUnitIncrement;
        headerGap = builder.headerGap;
        brandGap = builder.brandGap;
        workspaceGap = builder.workspaceGap;
        tabGap = builder.tabGap;
        cardGap = builder.cardGap;
        imageCardGap = builder.imageCardGap;
        headingGap = builder.headingGap;
        passwordCardGap = builder.passwordCardGap;
        gridHeadingBottomGap = builder.gridHeadingBottomGap;
        gridFilenameBottomGap = builder.gridFilenameBottomGap;
        capacityTopGap = builder.capacityTopGap;
        brandMarkSize = builder.brandMarkSize;
        buttonPaddingVertical = builder.buttonPaddingVertical;
        buttonPaddingHorizontal = builder.buttonPaddingHorizontal;
        headerTitleBaselineGap = builder.headerTitleBaselineGap;
        contentWidth = builder.contentWidth;
        hintTextLeftOffset = builder.hintTextLeftOffset;
    }

    public static ThemeConfig defaultConfig() {
        return builder().build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public Font font(int style, int size) {
        return new Font(fontFamily, style, size);
    }

    public Font boldFont(int size) {
        return font(Font.BOLD, size);
    }

    public Font plainFont(int size) {
        return font(Font.PLAIN, size);
    }

    public Border headerBorder() {
        return emptyBorder(headerPaddingTop, headerPaddingLeft, headerPaddingBottom, headerPaddingRight);
    }

    public Border workspaceBorder() {
        return emptyBorder(workspacePaddingTop, workspacePaddingLeft, workspacePaddingBottom, workspacePaddingRight);
    }

    public Border messageCardBorder() {
        return emptyBorder(messageCardPaddingTop, messageCardPaddingLeft,
                messageCardPaddingBottom, messageCardPaddingRight);
    }

    public Border resultCardBorder() {
        return emptyBorder(resultCardPaddingTop, resultCardPaddingLeft, resultCardPaddingBottom, resultCardPaddingRight);
    }

    public Border imageCardBorder() {
        return emptyBorder(imageCardPaddingTop, imageCardPaddingLeft, imageCardPaddingBottom, imageCardPaddingRight);
    }

    public Border passwordCardBorder() {
        return emptyBorder(passwordCardPaddingTop, passwordCardPaddingLeft,
                passwordCardPaddingBottom, passwordCardPaddingRight);
    }

    public Border pageBorder() {
        return emptyBorder(pagePaddingTop, pagePaddingLeft, pagePaddingBottom, pagePaddingRight);
    }

    public Border previewBorder() {
        return lineBorder(border);
    }

    public Border scrollBorder() {
        return lineBorder(border);
    }

    public Border textAreaBorder() {
        return emptyBorder(textAreaPaddingTop, textAreaPaddingLeft, textAreaPaddingBottom, textAreaPaddingRight);
    }

    public Border textFieldBorder() {
        return BorderFactory.createCompoundBorder(lineBorder(border),
                emptyBorder(textFieldPaddingTop, textFieldPaddingLeft, textFieldPaddingBottom, textFieldPaddingRight));
    }

    public Border statusBarBorder() {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(statusBarDividerWidth, 0, 0, 0, border),
                emptyBorder(statusBarPaddingTop, statusBarPaddingLeft,
                        statusBarPaddingBottom, statusBarPaddingRight));
    }

    public Insets spacingInsets(int top, int left, int bottom, int right) {
        return new Insets(top, left, bottom, right);
    }

    public Color buttonBackground(boolean primary, boolean active, boolean pressed, boolean rollover) {
        if (pressed) {
            return primary ? pressedPrimaryButton : pressedButton;
        }
        if (rollover) {
            return primary ? hoverPrimaryButton : hoverButton;
        }
        return primary ? accent : active ? activeButton : surface;
    }

    public void paintSquircle(Graphics graphics, int width, int height, Color fill, int radius) {
        paintSquircle(graphics, width, height, fill, border, radius);
    }

    public void paintSquircle(Graphics graphics, int width, int height, Color fill, Color outline, int radius) {
        Graphics2D g = (Graphics2D) graphics.create();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(fill);
        g.fillRoundRect(0, 0, width - 1, height - 1, radius, radius);
        g.setColor(outline);
        g.drawRoundRect(0, 0, width - 1, height - 1, radius, radius);
        g.dispose();
    }

    private Border emptyBorder(int top, int left, int bottom, int right) {
        return BorderFactory.createEmptyBorder(top, left, bottom, right);
    }

    private Border lineBorder(Color color) {
        return BorderFactory.createLineBorder(color, borderThickness);
    }

    public static final class Builder {
        private Color background = new Color(246, 247, 251);
        private Color surface = Color.WHITE;
        private Color ink = new Color(30, 39, 57);
        private Color muted = new Color(119, 130, 148);
        private Color accent = new Color(89, 83, 220);
        private Color border = new Color(230, 233, 241);
        private Color brandAccent = new Color(104, 98, 239);
        private Color headerSubtitle = new Color(184, 190, 207);
        private Color headerBadge = new Color(207, 211, 226);
        private Color footer = new Color(163, 170, 185);
        private Color activeButton = new Color(237, 236, 255);
        private Color pressedPrimaryButton = new Color(73, 67, 196);
        private Color pressedButton = new Color(242, 243, 248);
        private Color hoverPrimaryButton = new Color(105, 99, 231);
        private Color hoverButton = new Color(247, 248, 252);
        private String fontFamily = Font.SANS_SERIF;

        private int headerPaddingTop = 22;
        private int headerPaddingLeft = 34;
        private int headerPaddingBottom = 20;
        private int headerPaddingRight = 34;
        private int workspacePaddingTop = 22;
        private int workspacePaddingLeft = 34;
        private int workspacePaddingBottom = 18;
        private int workspacePaddingRight = 34;
        private int messageCardPaddingTop = 20;
        private int messageCardPaddingLeft = 22;
        private int messageCardPaddingBottom = 18;
        private int messageCardPaddingRight = 22;
        private int resultCardPaddingTop = 20;
        private int resultCardPaddingLeft = 22;
        private int resultCardPaddingBottom = 20;
        private int resultCardPaddingRight = 22;
        private int imageCardPaddingTop = 18;
        private int imageCardPaddingLeft = 22;
        private int imageCardPaddingBottom = 18;
        private int imageCardPaddingRight = 22;
        private int passwordCardPaddingTop = 18;
        private int passwordCardPaddingLeft = 22;
        private int passwordCardPaddingBottom = 20;
        private int passwordCardPaddingRight = 22;
        private int pagePaddingTop;
        private int pagePaddingLeft;
        private int pagePaddingBottom;
        private int pagePaddingRight;
        private int textAreaPaddingTop = 12;
        private int textAreaPaddingLeft = 12;
        private int textAreaPaddingBottom = 12;
        private int textAreaPaddingRight = 12;
        private int textFieldPaddingTop = 10;
        private int textFieldPaddingLeft = 12;
        private int textFieldPaddingBottom = 10;
        private int textFieldPaddingRight = 12;
        private int statusBarPaddingTop = 13;
        private int statusBarPaddingLeft = 34;
        private int statusBarPaddingBottom = 13;
        private int statusBarPaddingRight = 34;
        private int statusBarDividerWidth = 1;
        private int borderThickness = 1;

        private int windowMinWidth = 760;
        private int windowMinHeight = 700;
        private int windowWidth = 980;
        private int windowHeight = 820;
        private int fontSizeSmall = 10;
        private int fontSizeBody = 12;
        private int fontSizeButton = 13;
        private int fontSizeInput = 14;
        private int fontSizeHeading = 15;
        private int fontSizeMark = 16;
        private int fontSizeTitle = 20;
        private int cardRadius = 26;
        private int buttonRadius = 20;
        private int previewWidth = 138;
        private int previewHeight = 92;
        private int messageAreaHeight = 170;
        private int resultAreaHeight = 190;
        private int scrollUnitIncrement = 16;
        private int headerGap = 18;
        private int brandGap = 14;
        private int workspaceGap = 18;
        private int tabGap = 10;
        private int cardGap = 12;
        private int imageCardGap = 18;
        private int headingGap = 4;
        private int passwordCardGap = 10;
        private int gridHeadingBottomGap = 5;
        private int gridFilenameBottomGap = 12;
        private int capacityTopGap = 10;
        private int brandMarkSize = 44;
        private int buttonPaddingVertical = 12;
        private int buttonPaddingHorizontal = 18;
        private int headerTitleBaselineGap = 3;
        private int contentWidth = 500;
        private int hintTextLeftOffset = 2;

        private Builder() {
        }

        public Builder fontFamily(String value) { fontFamily = value; return this; }
        public Builder background(Color value) { background = value; return this; }
        public Builder surface(Color value) { surface = value; return this; }
        public Builder ink(Color value) { ink = value; return this; }
        public Builder muted(Color value) { muted = value; return this; }
        public Builder accent(Color value) { accent = value; return this; }
        public Builder border(Color value) { border = value; return this; }
        public Builder brandAccent(Color value) { brandAccent = value; return this; }
        public Builder headerSubtitle(Color value) { headerSubtitle = value; return this; }
        public Builder headerBadge(Color value) { headerBadge = value; return this; }
        public Builder footer(Color value) { footer = value; return this; }
        public Builder activeButton(Color value) { activeButton = value; return this; }
        public Builder pressedPrimaryButton(Color value) { pressedPrimaryButton = value; return this; }
        public Builder pressedButton(Color value) { pressedButton = value; return this; }
        public Builder hoverPrimaryButton(Color value) { hoverPrimaryButton = value; return this; }
        public Builder hoverButton(Color value) { hoverButton = value; return this; }

        public Builder headerPadding(int top, int left, int bottom, int right) {
            headerPaddingTop = top;
            headerPaddingLeft = left;
            headerPaddingBottom = bottom;
            headerPaddingRight = right;
            return this;
        }

        public Builder workspacePadding(int top, int left, int bottom, int right) {
            workspacePaddingTop = top;
            workspacePaddingLeft = left;
            workspacePaddingBottom = bottom;
            workspacePaddingRight = right;
            return this;
        }

        public Builder messageCardPadding(int top, int left, int bottom, int right) {
            messageCardPaddingTop = top;
            messageCardPaddingLeft = left;
            messageCardPaddingBottom = bottom;
            messageCardPaddingRight = right;
            return this;
        }

        public Builder resultCardPadding(int top, int left, int bottom, int right) {
            resultCardPaddingTop = top;
            resultCardPaddingLeft = left;
            resultCardPaddingBottom = bottom;
            resultCardPaddingRight = right;
            return this;
        }

        public Builder imageCardPadding(int top, int left, int bottom, int right) {
            imageCardPaddingTop = top;
            imageCardPaddingLeft = left;
            imageCardPaddingBottom = bottom;
            imageCardPaddingRight = right;
            return this;
        }

        public Builder passwordCardPadding(int top, int left, int bottom, int right) {
            passwordCardPaddingTop = top;
            passwordCardPaddingLeft = left;
            passwordCardPaddingBottom = bottom;
            passwordCardPaddingRight = right;
            return this;
        }

        public Builder pagePadding(int top, int left, int bottom, int right) {
            pagePaddingTop = top;
            pagePaddingLeft = left;
            pagePaddingBottom = bottom;
            pagePaddingRight = right;
            return this;
        }

        public Builder textAreaPadding(int top, int left, int bottom, int right) {
            textAreaPaddingTop = top;
            textAreaPaddingLeft = left;
            textAreaPaddingBottom = bottom;
            textAreaPaddingRight = right;
            return this;
        }

        public Builder textFieldPadding(int top, int left, int bottom, int right) {
            textFieldPaddingTop = top;
            textFieldPaddingLeft = left;
            textFieldPaddingBottom = bottom;
            textFieldPaddingRight = right;
            return this;
        }

        public Builder statusBarPadding(int top, int left, int bottom, int right) {
            statusBarPaddingTop = top;
            statusBarPaddingLeft = left;
            statusBarPaddingBottom = bottom;
            statusBarPaddingRight = right;
            return this;
        }

        public Builder statusBarDividerWidth(int value) { statusBarDividerWidth = value; return this; }
        public Builder borderThickness(int value) { borderThickness = value; return this; }

        public Builder windowMinWidth(int value) { windowMinWidth = value; return this; }
        public Builder windowMinHeight(int value) { windowMinHeight = value; return this; }
        public Builder windowWidth(int value) { windowWidth = value; return this; }
        public Builder windowHeight(int value) { windowHeight = value; return this; }
        public Builder fontSizeSmall(int value) { fontSizeSmall = value; return this; }
        public Builder fontSizeBody(int value) { fontSizeBody = value; return this; }
        public Builder fontSizeButton(int value) { fontSizeButton = value; return this; }
        public Builder fontSizeInput(int value) { fontSizeInput = value; return this; }
        public Builder fontSizeHeading(int value) { fontSizeHeading = value; return this; }
        public Builder fontSizeMark(int value) { fontSizeMark = value; return this; }
        public Builder fontSizeTitle(int value) { fontSizeTitle = value; return this; }
        public Builder cardRadius(int value) { cardRadius = value; return this; }
        public Builder buttonRadius(int value) { buttonRadius = value; return this; }
        public Builder previewWidth(int value) { previewWidth = value; return this; }
        public Builder previewHeight(int value) { previewHeight = value; return this; }
        public Builder messageAreaHeight(int value) { messageAreaHeight = value; return this; }
        public Builder resultAreaHeight(int value) { resultAreaHeight = value; return this; }
        public Builder scrollUnitIncrement(int value) { scrollUnitIncrement = value; return this; }
        public Builder headerGap(int value) { headerGap = value; return this; }
        public Builder brandGap(int value) { brandGap = value; return this; }
        public Builder workspaceGap(int value) { workspaceGap = value; return this; }
        public Builder tabGap(int value) { tabGap = value; return this; }
        public Builder cardGap(int value) { cardGap = value; return this; }
        public Builder imageCardGap(int value) { imageCardGap = value; return this; }
        public Builder headingGap(int value) { headingGap = value; return this; }
        public Builder passwordCardGap(int value) { passwordCardGap = value; return this; }
        public Builder gridHeadingBottomGap(int value) { gridHeadingBottomGap = value; return this; }
        public Builder gridFilenameBottomGap(int value) { gridFilenameBottomGap = value; return this; }
        public Builder capacityTopGap(int value) { capacityTopGap = value; return this; }
        public Builder brandMarkSize(int value) { brandMarkSize = value; return this; }
        public Builder buttonPaddingVertical(int value) { buttonPaddingVertical = value; return this; }
        public Builder buttonPaddingHorizontal(int value) { buttonPaddingHorizontal = value; return this; }
        public Builder headerTitleBaselineGap(int value) { headerTitleBaselineGap = value; return this; }
        public Builder contentWidth(int value) { contentWidth = value; return this; }
        public Builder hintTextLeftOffset(int value) { hintTextLeftOffset = value; return this; }

        public ThemeConfig build() {
            Objects.requireNonNull(fontFamily, "Font family cannot be null.");
            Objects.requireNonNull(background, "Background color cannot be null.");
            Objects.requireNonNull(surface, "Surface color cannot be null.");
            Objects.requireNonNull(ink, "Ink color cannot be null.");
            Objects.requireNonNull(muted, "Muted color cannot be null.");
            Objects.requireNonNull(accent, "Accent color cannot be null.");
            Objects.requireNonNull(border, "Border color cannot be null.");
            Objects.requireNonNull(brandAccent, "Brand accent color cannot be null.");
            Objects.requireNonNull(headerSubtitle, "Header subtitle color cannot be null.");
            Objects.requireNonNull(headerBadge, "Header badge color cannot be null.");
            Objects.requireNonNull(footer, "Footer color cannot be null.");
            Objects.requireNonNull(activeButton, "Active button color cannot be null.");
            Objects.requireNonNull(pressedPrimaryButton, "Pressed primary button color cannot be null.");
            Objects.requireNonNull(pressedButton, "Pressed button color cannot be null.");
            Objects.requireNonNull(hoverPrimaryButton, "Hover primary button color cannot be null.");
            Objects.requireNonNull(hoverButton, "Hover button color cannot be null.");
            validate();
            return new ThemeConfig(this);
        }

        private void validate() {
            requirePositive(windowMinWidth, "Minimum window width");
            requirePositive(windowMinHeight, "Minimum window height");
            requirePositive(windowWidth, "Window width");
            requirePositive(windowHeight, "Window height");
            requirePositive(fontSizeSmall, "Small font size");
            requirePositive(fontSizeBody, "Body font size");
            requirePositive(fontSizeButton, "Button font size");
            requirePositive(fontSizeInput, "Input font size");
            requirePositive(fontSizeHeading, "Heading font size");
            requirePositive(fontSizeMark, "Mark font size");
            requirePositive(fontSizeTitle, "Title font size");
            requirePositive(previewWidth, "Preview width");
            requirePositive(previewHeight, "Preview height");
            requirePositive(messageAreaHeight, "Message area height");
            requirePositive(resultAreaHeight, "Result area height");
            requirePositive(contentWidth, "Content width");
            requireNonNegative(scrollUnitIncrement, cardRadius, buttonRadius, borderThickness, statusBarDividerWidth);
            if (windowWidth < windowMinWidth || windowHeight < windowMinHeight) {
                throw new IllegalArgumentException("Window dimensions must meet the configured minimum size.");
            }
            requireNonNegative(headerPaddingTop, headerPaddingLeft, headerPaddingBottom, headerPaddingRight,
                    workspacePaddingTop, workspacePaddingLeft, workspacePaddingBottom, workspacePaddingRight,
                    messageCardPaddingTop, messageCardPaddingLeft, messageCardPaddingBottom, messageCardPaddingRight,
                    resultCardPaddingTop, resultCardPaddingLeft, resultCardPaddingBottom, resultCardPaddingRight,
                    imageCardPaddingTop, imageCardPaddingLeft, imageCardPaddingBottom, imageCardPaddingRight,
                    passwordCardPaddingTop, passwordCardPaddingLeft, passwordCardPaddingBottom, passwordCardPaddingRight,
                    pagePaddingTop, pagePaddingLeft, pagePaddingBottom, pagePaddingRight,
                    textAreaPaddingTop, textAreaPaddingLeft, textAreaPaddingBottom, textAreaPaddingRight,
                    textFieldPaddingTop, textFieldPaddingLeft, textFieldPaddingBottom, textFieldPaddingRight,
                    statusBarPaddingTop, statusBarPaddingLeft, statusBarPaddingBottom, statusBarPaddingRight,
                    headerGap, brandGap, workspaceGap, tabGap, cardGap, imageCardGap, headingGap, passwordCardGap,
                    gridHeadingBottomGap, gridFilenameBottomGap, capacityTopGap, brandMarkSize,
                    buttonPaddingVertical, buttonPaddingHorizontal, headerTitleBaselineGap, hintTextLeftOffset);
        }

        private void requirePositive(int value, String property) {
            if (value <= 0) {
                throw new IllegalArgumentException(property + " must be greater than zero.");
            }
        }

        private void requireNonNegative(int... values) {
            for (int value : values) {
                if (value < 0) {
                    throw new IllegalArgumentException("Theme spacing, radius, and border values cannot be negative.");
                }
            }
        }
    }
}
