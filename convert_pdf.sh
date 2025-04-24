#!/bin/bash

# Create output directory if it doesn't exist
mkdir -p text_version

# Loop through all PDF files in the current directory
for pdf in *.pdf; do
    # Skip if no PDF files are found
    [ -e "$pdf" ] || continue

    # Extract filename without extension
    filename=$(basename "$pdf" .pdf)

    # Convert PDF to text
    pdftotext "$pdf" "text_version/${filename}.txt"

    # Print status message
    echo "Converted: $pdf -> text_version/${filename}.txt"
done

echo "All PDFs have been converted!"

