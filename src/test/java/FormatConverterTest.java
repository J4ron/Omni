import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import sh.convexio.input.ExtensionMimeType;
import sh.convexio.input.FormatConverter;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class FormatConverterTest {

    static ExtensionMimeType mimeType;

    @BeforeAll
    static void setup() {
        mimeType = new ExtensionMimeType();
    }

    FormatConverter<String, String> dummyConverter = new FormatConverter<>() {
        @Override
        public String convertTo(String source) {
            if(source == null) throw new IllegalArgumentException("Source cannot be null");
            return source.toUpperCase();
        }
        @Override
        public String convertFrom(String target) {
            if(target == null) throw new IllegalArgumentException("Target cannot be null");
            return target.toLowerCase();
        }
    };

    @Test
    void testConvertTo_validInput() {
        assertEquals("HELLO", dummyConverter.convertTo("hello"));
    }

    @Test
    void testConvertTo_nullInput() {
        assertThrows(IllegalArgumentException.class, () -> dummyConverter.convertTo(null));
    }

    @Test
    void testConvertFrom_validInput() {
        assertEquals("hello", dummyConverter.convertFrom("HELLO"));
    }

    @Test
    void testConvertFrom_nullInput() {
        assertThrows(IllegalArgumentException.class, () -> dummyConverter.convertFrom(null));
    }

    @Test
    void testConvertRoundTrip_validInput() {
        String input = "TestString";
        String converted = dummyConverter.convertFrom(input);
        String back = dummyConverter.convertFrom(converted);
        assertEquals(input.toLowerCase(), back);
    }

    @Test
    void testConvertTo_emptyString() {
        assertEquals("", dummyConverter.convertTo(""));
    }

    @Test
    void testSupportedExtension_docx() {
        Optional<String> ext = mimeType.getExtensionIfSupported("document.docx");
        assertTrue(ext.isPresent());
        assertEquals("docx", ext.get());
    }

    @Test
    void testSupportedExtension_html() {
        Optional<String> ext = mimeType.getExtensionIfSupported("index.html");
        assertFalse(ext.isPresent());
    }

    @Test
    void testSupportedExtension_withCurlyBracesInName() {
        Optional<String> ext = mimeType.getExtensionIfSupported("file.{}.xml");
        assertTrue(ext.isPresent());
        assertEquals("xml", ext.get());
    }

    @Test
    void testExtensionIfSupported() {
        Optional<String> ext = mimeType.getExtensionIfSupported(null);
        assertFalse(ext.isPresent());
    }

    @Test
    void testSupportedExtension_noExtension() {
        Optional<String> ext = mimeType.getExtensionIfSupported("filename");
        assertFalse(ext.isPresent());
    }

    @Test
    void testSupportedExtension_multipleDots() {
        Optional<String> ext = mimeType.getExtensionIfSupported("archive.tar.gz");
        assertTrue(ext.isEmpty());
    }

    @Test
    void testSupportedExtension_caseInsensitive() {
        Optional<String> ext1 = mimeType.getExtensionIfSupported("file.XML");
        assertTrue(ext1.isPresent());
        assertEquals("xml", ext1.get());

        Optional<String> ext2 = mimeType.getExtensionIfSupported("file.Docx");
        assertTrue(ext2.isPresent());
        assertEquals("docx", ext2.get());
    }

    @Test
    void testSupportedExtension_emptyString() {
        Optional<String> ext = mimeType.getExtensionIfSupported("");
        assertFalse(ext.isPresent());
    }

    @Test
    void testSupportedExtension_extensionWithSpaces() {
        Optional<String> ext1 = mimeType.getExtensionIfSupported("file. xml");
        assertFalse(ext1.isPresent());

        Optional<String> ext2 = mimeType.getExtensionIfSupported("file.xml ");
        assertTrue(ext2.isPresent());
        assertEquals("xml", ext2.get());
    }

    @Test
    void testSupportedExtension_extensionWithSpecialChars() {
        Optional<String> ext = mimeType.getExtensionIfSupported("file.na-me");
        assertFalse(ext.isPresent());
    }

    @Test
    void testSupportedExtension_onlyDotAtEnd() {
        Optional<String> ext = mimeType.getExtensionIfSupported("file.");
        assertFalse(ext.isPresent());
    }

    @Test
    void testSupportedExtension_unsupportedExtension() {
        Optional<String> ext = mimeType.getExtensionIfSupported("file.unsupported");
        assertFalse(ext.isPresent());
    }

    @Test
    void testFilenameWithLeadingSpace() {
        Optional<String> ext = mimeType.getExtensionIfSupported(" file.docx");
        // Expected: probably empty because extension extraction will fail or include space
        assertFalse(ext.isPresent());
    }

    @Test
    void testFilenameWithTrailingSpace() {
        Optional<String> ext = mimeType.getExtensionIfSupported("file.docx ");
        // Your method trims extension so it should return "docx"
        assertTrue(ext.isPresent());
        assertEquals("docx", ext.get());
    }

    @Test
    void testFilenameStartingWithDot() {
        Optional<String> ext = mimeType.getExtensionIfSupported(".docx");
        // Extension extraction should work (ext = "docx"), so true
        assertTrue(ext.isPresent());
        assertEquals("docx", ext.get());
    }

    @Test
    void testOnlyDotFilename() {
        Optional<String> ext = mimeType.getExtensionIfSupported(".");
        assertFalse(ext.isPresent());
    }

    @Test
    void testConvertFrom_emptyString() {
        assertEquals("", dummyConverter.convertFrom(""));
    }

    @Test
    void testConvertTo_specialChars() {
        assertEquals("!@#$$%^", dummyConverter.convertTo("!@#$$%^"));
    }

    @Test
    void testConvertFrom_specialChars() {
        assertEquals("!@#$$%^", dummyConverter.convertFrom("!@#$$%^"));
    }

    @Test
    void testConvertTo_unicode() {
        assertEquals("😀👍", dummyConverter.convertTo("😀👍"));
    }

    @Test
    void testConvertFrom_unicode() {
        assertEquals("😀👍", dummyConverter.convertFrom("😀👍"));
    }

}
