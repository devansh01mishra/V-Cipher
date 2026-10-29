# V-Cipher

A desktop application to hide data inside images and extract data from images.

## How to manage program files

The `program.sh` script builds, runs, tests, and packages the application. Packaging requires a JDK with `jpackage` and creates a platform-specific application image in `dist/package/V-Cipher`.

#### CREATE BINARY :-

    ./program.sh -b 

#### RUN BINARY

    ./program.sh -r 

#### TEST BINARY

    ./program.sh -t 

#### CREATE APPLICATION IMAGE

    ./program.sh -o <VERSION>

## Modules