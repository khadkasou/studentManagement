$docPath = Join-Path (Get-Location) "design-toc (2).docx"
$zip = [System.IO.Compression.ZipFile]::OpenRead($docPath)
$entry = $zip.Entries | Where-Object { $_.FullName -eq "word/document.xml" }
if ($entry) {
    $stream = $entry.Open()
    $reader = New-Object System.IO.StreamReader($stream)
    $xml = $reader.ReadToEnd()
    $reader.Close()
    $stream.Close()
    
    # Remove XML tags and clean up whitespace
    $text = $xml -replace '<[^>]+>', ' ' -replace '\s+', ' '
    Write-Output $text
} else {
    Write-Host "document.xml not found"
}
$zip.Dispose()
