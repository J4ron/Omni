<p align="center">
  <img src="https://convexio.sh/logo.png" alt="Project Logo" width="200"/>
</p>

<h1 align="center">Convexio</h1>

<p align="center">
  Modern, scalable and efficient solution for fast local file conversion via CLI.
  <br />
  <a href="https://convexio.sh"><strong>→ Website</strong></a>
  ·
  <a href="https://github.com/youruser/yourrepo/issues">Report Bug</a>
  ·
  <a href="https://github.com/youruser/yourrepo/issues">Request Feature</a>
</p>

---

## 🚀 Features

* ⚡ Schnelle & effiziente Verarbeitung von Dateien lokal via CLI
* 🧠 Clean Architecture mit Fokus auf Testbarkeit
* 🔌 Plug-in-System für einfache Erweiterbarkeit
* 🧪 100% getestet mit JUnit

---

## 🔠 Installation

```bash
git clone https://github.com/youruser/yourrepo.git
cd yourrepo
./gradlew build
```

---

## 🧪 Tests ausführen

```bash
./gradlew test
```

Oder in IntelliJ / WebStorm über das Test-Menü.

---

## 🤰 Beispielverwendung (Java)

```java
FormatConverter<String, String> converter = new MyConverter();
String result = converter.convertTo("input");
```

---

## 🧰 Beispielverwendung (CLI)

```bash
convexio -i ./input/file.docx -o ./output/file.txt -p pretty
```

**Parameter:**

* `-i` oder `--input` – Pfad zur Eingabedatei
* `-o` oder `--output` – Pfad zur Ausgabedatei
* `-p` oder `--profile` – Optionales Konvertierungsprofil (`pretty`, `raw`, ...)

> Flags können je nach Implementierung angepasst werden.

---

## 🌐 Projekt-Website

[https://convexio.sh](https://convexio.sh)

---

## 📄 Lizenz

Dieses Projekt steht unter der [MIT Lizenz](LICENSE).

---

## 👤 Autor

**dev.jaron** – [GitHub](https://github.com/devjaron) • [Website](https://convexio.sh)

---

> Crafted with ❤️ by dev.jaron
