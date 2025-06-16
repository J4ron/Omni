<p align="center">
 <img src="src/main/resources/nobglogoasci.png" alt="Convexio Logo">
</p>

<h1 align="center">Convexio</h1>

<p align="center">
  Modern, scalable, and efficient solution for fast local file conversion via CLI.<br />
  <a href="https://convexio.sh"><strong>→ Website</strong></a>
  ·
  <a href="https://github.com/youruser/yourrepo/issues">Report Bug</a>
  ·
  <a href="https://github.com/youruser/yourrepo/issues">Request Feature</a>
</p>

---

## 📦 What is Convexio?

**Convexio** is your no-cloud, developer-first CLI tool for blazing fast file format conversion. No telemetry, no lock-in — just raw performance and clean extensibility.

> Born from the need for local-first tools with real power, Convexio is engineered for devs who value speed, control, and clarity. ⚙️

---

## 🤔 Why Convexio?

* You don’t want cloud uploads.
* You want extensibility without bloat.
* You care about performance.

Convexio is built for:

* Developers 🧑‍💻
* Data wranglers 📊
* Automation engineers 🤖
* Security-conscious users 🔐

---

## 🔧 How It Works

Convexio uses a plugin-like strategy system to select and run the right converter for each file type. Its clean, hexagonal architecture ensures easy testing, maintenance, and extension.

![Architecture Diagram](src/main/resources/convexio-architecture.png) <!-- Optional placeholder -->

---

## 🚀 Features

* ⚡ Fast & efficient local file processing via CLI
* 🧠 Clean architecture with a focus on testability
* 🔌 Plugin system for easy extensibility
* 🔐 Optional license key system with secure activation flow
* 🧪 100% tested with JUnit
* 🧱 Hexagonal architecture (Ports & Adapters)

---

## 💠 Getting Started

```bash
git clone https://github.com/youruser/yourrepo.git
cd yourrepo
./gradlew build
```

To run:

```bash
java -jar convexio.jar convert -i ./input.docx -o ./output.pdf -r docxReader -t pdf
```

---

## ⚙️ Activation System

Convexio supports a one-time license system with local config storage. Activation is CLI-based:

```bash
convexio upgrade -k "YOUR_LICENSE_KEY"
convexio upgrade -d    # Deactivates current device
```

* 🔑 License keys are one-time purchasable
* 🖥️ Bound to your device ID
* 📁 Stored securely in local config file
* 📬 Delivered via email after purchase

---

## 🧪 Running Tests

```bash
./gradlew test
```

Or run tests via IntelliJ.

---

## ✨ Example Usage (Java)

```java
FormatConverter<String, String> converter = new MyConverter();
String result = converter.convertTo("input");
```

---

## 🧰 Example Usage (CLI)

<img src="src/main/resources/climockup.png" alt="CLI Usage Screenshot">

```bash
convexio -i ./input/file.docx -o ./output/file.txt -p pretty
```

**Parameters:**

* `-i` or `--input` – Path to the input file
* `-o` or `--output` – Path to the output file
* `-r` or `--rename` – Renames original path

> Flags may vary depending on implementation.

---

## 🌐 Project Website

[https://convexio.sh](https://convexio.sh)

---

## 🤝 Contributing

Pull requests welcome! For major changes, open an issue first to discuss what you’d like to change.

---

## 📄 License

This project is licensed under the [MIT License](LICENSE).

---

## 👤 Author

**dev.jaron** – [GitHub](https://github.com/devjaron) • [Website](https://convexio.sh)

---

> Crafted with ❤️ by dev.jaron – Last updated: June 2025 ✅
