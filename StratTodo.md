
# Convexio - Implementierungsliste für Konvertierungsstrategien

## PDF-Verarbeitung
- [ ] **PdfToWordStrategy.java**
    - Implementierung der PDF → DOCX Konvertierung
    - Benötigte Bibliotheken: Apache POI, PDFBox
    - Hauptfunktionen: Textextraktion, Formatierung bewahren

- [ ] **MergePdfStrategy.java**
    - PDFs zusammenführen
    - Verwendung von PDFBox
    - Möglichkeit zur Auswahl der Seitenreihenfolge

- [ ] **SplitPdfStrategy.java**
    - PDF in mehrere Dateien aufteilen
    - Optionen: nach Seitenzahl, nach Lesezeichen

- [ ] **FillFormStrategy.java**
    - PDF-Formulare ausfüllen und Daten extrahieren
    - AcroForm-Unterstützung
    - Formularfeld-Mapping

- [ ] **SignPdfStrategy.java**
    - Digitale Signatur von PDFs
    - Zertifikatshandling
    - Sichtbare/unsichtbare Signaturen

- [ ] **PreflightCheckStrategy.java**
    - PDF/A-1b Validierung
    - Konformitätsprüfung
    - Fehlerbericht-Generierung

## Bild-Verarbeitung
- [ ] **ImageConversionStrategy.java**
    - Unterstützung für JPG ⇄ PNG
    - PNG → PDF Konvertierung
    - Bildoptimierung und Qualitätseinstellungen

- [ ] **SaveAsImageStrategy.java**
    - PDF → PNG/JPEG Konvertierung
    - DPI-Einstellungen
    - Einzelseiten-Export

## Office-Dokumente
- [ ] **OfficeToPdfStrategy.java**
    - DOCX → PDF
    - XLSX → PDF
    - PPTX → PDF
    - Formatierungserhaltung

## Datenformat-Konvertierung
- [ ] **JsonToCsvStrategy.java**
    - JSON → CSV Konvertierung
    - Verschachtelungshandling
    - Header-Generierung

- [ ] **CsvToJsonStrategy.java**
    - CSV → JSON Back-Convert
    - Typerkennung
    - Spaltenmapping

- [ ] **YamlToJsonStrategy.java**
    - YAML ⇄ JSON Konvertierung
    - Kommentarhandling
    - Ankerunterstützung

## Text-Verarbeitung
- [ ] **TxtConversionStrategy.java**
    - TXT → PDF (erweiterte Version)
    - TXT → DOCX
    - Formatierungsoptionen

## Prioritäten
1. PdfToWordStrategy.java (Hohe Nachfrage)
2. ImageConversionStrategy.java (Grundlegende Bildverarbeitung)
3. OfficeToPdfStrategy.java (Häufig benötigt)
4. JsonToCsvStrategy.java & CsvToJsonStrategy.java (Datenverarbeitung)
5. Weitere PDF-Werkzeuge (MergePdf, SplitPdf, etc.)

## Benötigte Bibliotheken
- Apache PDFBox (PDF-Verarbeitung)
- Apache POI (Office-Dokumente)
- Jackson (JSON/YAML)
- OpenPDF (PDF-Generierung)
- ImageMagick/Java ImageIO (Bildverarbeitung)

## Implementierungshinweise
- Alle Strategien müssen das `IConversionStrategy`-Interface implementieren
- Fehlerbehandlung und Logging hinzufügen
- Unit-Tests für jede Strategie erstellen
- Dokumentation der Nutzungsbeispiele