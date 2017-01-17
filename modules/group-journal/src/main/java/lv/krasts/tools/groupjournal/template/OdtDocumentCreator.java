package lv.krasts.tools.groupjournal.template;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import net.sf.jooreports.templates.DocumentTemplate;
import net.sf.jooreports.templates.DocumentTemplateException;
import net.sf.jooreports.templates.DocumentTemplateFactory;

import org.apache.commons.io.IOUtils;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;

import freemarker.template.Configuration;

public class OdtDocumentCreator {

	public void create(InputStream template, Map<String, Object> beans,
			OutputStream output) {
		Preconditions.checkNotNull(template, "Template can't be null");
		DocumentTemplateFactory documentTemplateFactory = new DocumentTemplateFactory();

		Configuration configuration = documentTemplateFactory
				.getFreemarkerConfiguration();
		configuration.setWhitespaceStripping(true);

		try {
			DocumentTemplate documentTemplate = documentTemplateFactory
					.getTemplate(template);
			documentTemplate.createDocument(beans, output);
			output.flush();
			IOUtils.closeQuietly(output);
		} catch (IOException e) {
			Throwables.propagate(e);
		} catch (DocumentTemplateException e) {
			Throwables.propagate(e);
		}
	}

}
