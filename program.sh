#!/usr/bin/env bash
set -e

BUILD() {
    echo "[*] Cleaning old builds..."
    rm -rf bin
    mkdir -p bin output

    echo "[*] Compiling source files..."
    find src -name "*.java" > sources.txt
    javac -d bin @sources.txt
    rm sources.txt

    echo "[✓] Build successful! Compiled classes saved to /bin"
}

RUN() {
    if [ ! -d "bin" ]; then
        echo "[!] Binaries not found. Building fresh binary..."
        BUILD
    fi

    echo "[*] Launching V-Cipher..."
    java -cp bin com.vcipher.Main
}

TEST() {
    echo "[*] Compiling main application..."
    BUILD

    echo "[*] Compiling test suite..."
    mkdir -p bin-test
    find test -name "*.java" > test_sources.txt

    if [ -s test_sources.txt ]; then
        javac -cp bin -d bin-test @test_sources.txt
        rm test_sources.txt
        
        echo "[*] Running tests..."
        java -cp "bin:bin-test" com.vcipher.test.TestRunner
    else
        echo "[!] No test files found in test/ directory yet."
        rm test_sources.txt
    fi
}

OUTPUT() {
    echo "[*] Checking for build binaries..."
    if [ ! -d "bin" ]; then
        echo "[!] /bin directory not found. Building fresh binary..."
        BUILD
    fi

    echo "[*] Creating distributable directory..."
    mkdir -p dist

    echo "[*] Packaging compiled classes into executable JAR..."
    jar --create --file="dist/vcipher.jar" --main-class="com.vcipher.Main" -C bin .

    echo "[*] Cleaning old package outputs..."
    rm -rf "dir/package"

    echo "[*] Building native application image using jpackage..."
    jpackage \
        --input "dir" \
        --name "V-Cipher" \
        --app-version "$1" \
        --main-jar "vcipher.jar" \
        --main-class "com.vcipher.Main" \
        --type app-image \
        --dest "dir/package" \
    
    echo "[✓] Success! Native application generated at: dist/package/V-Cipher"
}

choice=$1

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
        OUTPUT $2
    ;;
esac