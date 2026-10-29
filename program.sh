#!/usr/bin/env bash
set -euo pipefail

PROJECT_DIR="$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" && pwd)"
cd "$PROJECT_DIR"

BUILD_DIR="$PROJECT_DIR/bin"
TEST_BUILD_DIR="$PROJECT_DIR/bin-test"
DIST_DIR="$PROJECT_DIR/dist"

BUILD() {
    echo "[*] Cleaning old builds..."
    rm -rf "$BUILD_DIR"
    mkdir -p "$BUILD_DIR"

    echo "[*] Compiling source files..."
    local sources_file
    sources_file="$(mktemp)"
    find src -name "*.java" -print > "$sources_file"
    javac -d "$BUILD_DIR" @"$sources_file"
    rm -f "$sources_file"

    echo "[✓] Build successful! Compiled classes saved to $BUILD_DIR"
}

RUN() {
    if [ ! -d "$BUILD_DIR" ]; then
        echo "[!] Binaries not found. Building fresh binary..."
        BUILD
    fi

    echo "[*] Launching V-Cipher..."
    java -cp "$BUILD_DIR" com.vcipher.Main
}

TEST() {
    echo "[*] Compiling main application..."
    BUILD

    echo "[*] Compiling test suite..."
    rm -rf "$TEST_BUILD_DIR"
    mkdir -p "$TEST_BUILD_DIR"
    local sources_file
    sources_file="$(mktemp)"
    find test -name "*.java" -print > "$sources_file"

    if [ -s "$sources_file" ]; then
        javac -cp "$BUILD_DIR" -d "$TEST_BUILD_DIR" @"$sources_file"
        rm -f "$sources_file"
        
        echo "[*] Running tests..."
        java -cp "$BUILD_DIR:$TEST_BUILD_DIR" TestRunner
    else
        echo "[!] No test files found in test/ directory yet."
        rm -f "$sources_file"
    fi
}

OUTPUT() {
    local version="${1:-}"
    if [[ ! "$version" =~ ^[0-9][A-Za-z0-9.+-]*$ ]]; then
        echo "[!] A valid version is required. Usage: ./program.sh -o <VERSION>" >&2
        return 2
    fi

    if ! command -v jpackage >/dev/null 2>&1; then
        echo "[!] jpackage was not found. Install/use a JDK that includes jpackage." >&2
        return 1
    fi

    echo "[*] Compiling application for packaging..."
    BUILD

    local input_dir="$DIST_DIR/package-input"
    local package_dir="$DIST_DIR/package"
    local app_image="$package_dir/V-Cipher"

    echo "[*] Preparing application JAR..."
    rm -rf "$input_dir"
    mkdir -p "$input_dir" "$package_dir"
    jar --create --file="$input_dir/vcipher.jar" --main-class="com.vcipher.Main" -C "$BUILD_DIR" .

    echo "[*] Removing previous application image..."
    rm -rf "$app_image"

    echo "[*] Building native application image using jpackage..."
    jpackage \
        --input "$input_dir" \
        --name "V-Cipher" \
        --app-version "$version" \
        --main-jar "vcipher.jar" \
        --main-class "com.vcipher.Main" \
        --type app-image \
        --dest "$package_dir"
    
    echo "[✓] Success! Native application image generated at: $app_image"
}

choice="${1:-}"

case $choice in
    "-b")
        BUILD
    ;;

    "-r")
        RUN
    ;;
    
    "-t")
        TEST
    ;;

    "-o")
        OUTPUT "${2:-}"
    ;;

    *)
        echo "Usage: ./program.sh {-b|-r|-t|-o <VERSION>}" >&2
        exit 2
    ;;
esac