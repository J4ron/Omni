# Convexio - Development Guide

## Überblick
Convexio ist ein CLI-basierter Dateikonverter für die Umwandlung von Textdateien in PDF-Format.

## Design Patterns
### Strategy Pattern
- TxtToPdfStrategy für die Konvertierung von Text zu PDF
- StrategyRegistry zur Verwaltung der Konvertierungsstrategien
- Erweiterbar für weitere Formate

## Architektur

### Hexagonale Architektur
sh.omni/ 
├── domain/ 
│ ├── model/ # Datenmodelle (FileContent, ConversionRequest, ConversionResult) 
│ ├── ports/ # Interfaces (IConverter, IFileSystem) 
│ └── services/ # Domain Services (ConverterService) 
├── adapters/ │ ├── in/ # Input Adapter (CliAdapter) 
│ └── out/ # Output Adapter (FileIoAdapter) 
└── strategies/ # Konvertierungsstrategien 
├── IConversionStrategy 
├── StrategyRegistry 
└── TxtToPdfStrategy


## Verwendete Libraries
### Datei-Konvertierung
| Name | Lizenz | Beschreibung |
|------|---------|-------------|
| Apache PDFBox | Apache | PDF Erstellung/Manipulation |

### Utilities
| Name | Lizenz | Verwendung |
|------|---------|-----------|
| Apache Commons IO | Apache | File-Handling |
| SLF4J + Logback | MIT | Logging |

## Domain Model
 
Domain Models record FileContent(byte[] content, String format) {} 

record ConversionRequest(String inputPath, String outputPath, String targetFormat) {} 

record ConversionResult(boolean success, String message, FileContent convertedContent) {}

Ports interface IConverter { ConversionResult convert(ConversionRequest request); }

interface IFileSystem { FileContent readFile(String path); void writeFile(String path, FileContent content); }


## Strategy Pattern Implementation

java interface IConversionStrategy 
{ boolean canHandle(String sourceFormat, String targetFormat); 
FileContent convert(FileContent source, String targetFormat); }

class StrategyRegistry { List strategies; ## Build
- Gradle
- JUnit 5 für Tests
- Java 17

## Nächste Schritte
1. Implementation der Domain Models
2. Implementation der Ports
3. PDF-Konvertierung in TxtToPdfStrategy
4. Integration der Adapter
5. Unit Tests

## Testing
- Unit Tests für:
  - Konvertierungsstrategien
  - Domain Services
  - Adapter
IConversionStrategy findStrategy(String sourceFormat, String targetFormat); }
