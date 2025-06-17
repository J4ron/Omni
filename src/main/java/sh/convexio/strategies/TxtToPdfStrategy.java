package sh.convexio.strategies;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import sh.convexio.domain.model.FileContent;
import sh.convexio.domain.ports.IPdfConstants;

/**
 * A strategy for converting text (.txt) files to PDF documents.
 * This class implements both the {@link IConversionStrategy} interface
 * for file format conversion logic and the {@link IPdfConstants} interface
 * to use predefined constants for PDF formatting.

 * Responsibilities:
 * - Determines whether this strategy can handle a specific source-to-target format conversion.
 * - Performs the conversion from a text file to a PDF, applying defined margins, fonts, and line spacing.

 * Features:
 * - Reads the content of a text file and processes its lines for inclusion in a PDF document.
 * - Ensures proper text formatting using a fixed default font, margins, and spacing as per {@link IPdfConstants}.
 * - Produces a byte array representation of the created PDF, encapsulated in a {@link FileContent} object.

 * Error Handling:
 * - Wraps exceptions related to file handling, PDF generation, or stream processing in runtime exceptions.

 * Methods:
 * - {@code canHandle(String sourceFormat, String targetFormat)}: Determines if the strategy supports converting
 *   between the given source and target formats.
 * - {@code convert(FileContent source, String targetFormat)}: Executes the text-to-PDF conversion process.
 */

public class TxtToPdfStrategy implements IConversionStrategy, IPdfConstants
{

    /**
     * Determines if the current strategy can handle the conversion between the given source format
     * and target format.
     *
     * @param sourceFormat the format of the source file to be converted
     * @param targetFormat the desired target format for the conversion
     * @return true if this conversion strategy supports converting from the source format to
     *         the target format, otherwise false
     */
    @Override
    public boolean canHandle(String sourceFormat, String targetFormat)
    {
        return "txt".equalsIgnoreCase(sourceFormat)
                && "pdf".equalsIgnoreCase(targetFormat);
    }

    /**
     * Converts the content of a source file into the specified target format.
     * This implementation reads a text file content and converts it into a PDF file,
     * applying predefined formatting for font, margins, and line spacing.
     *
     * @param source the source file to be converted, encapsulated as a {@link FileContent} object
     * @param targetFormat the desired target format for the conversion, e.g., "pdf"
     * @return a new {@link FileContent} object containing the converted content in the target format
     * @throws RuntimeException if any error occurs during the conversion process
     */
    @Override
    public FileContent convert(FileContent source, String targetFormat)
    {
        try (PDDocument document = new PDDocument())
        {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page))
            {
                contentStream.beginText();
                setDocumentFont(contentStream);
                contentStream.newLineAtOffset(DEFAULT_MARGIN_LEFT, DEFAULT_MARGIN_TOP);

                String text = new String(source.content(), StandardCharsets.UTF_8);
                String[] lines = text.split("\n");

                for (String line : lines)
                {
                    contentStream.showText(line);
                    contentStream.newLineAtOffset(0, DEFAULT_LINE_SPACING);
                }

                contentStream.endText();
            }

            try (ByteArrayOutputStream baos = new ByteArrayOutputStream())
            {
                document.save(baos);
                return new FileContent(
                        source.filename(),
                        baos.toByteArray(),
                        "pdf"
                );
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException("PDF conversion failed: " + e.getMessage(), e);
        }
    }

    /**
     * Sets the font and font size for the given {@link PDPageContentStream}.
     * The font is set to the default font as defined in {@link IPdfConstants}.
     *
     * @param contentStream the {@link PDPageContentStream} to apply the font settings to
     * @throws RuntimeException if an {@link IOException} occurs while setting the font
     */
    private void setDocumentFont(PDPageContentStream contentStream)
    {
        try
        {
            contentStream.setFont(new PDType1Font(DEFAULT_FONT), IPdfConstants.DEFAULT_FONT_SIZE);
        }
        catch (IOException e)
        {
            throw new RuntimeException("Failed to set font: " + e.getMessage(), e);
        }
    }
}
