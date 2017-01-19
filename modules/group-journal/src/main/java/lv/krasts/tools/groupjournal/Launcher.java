package lv.krasts.tools.groupjournal;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import lv.krasts.tools.groupjournal.parser.HtmlParser;
import lv.krasts.tools.groupjournal.parser.Model;
import lv.krasts.tools.groupjournal.template.OdtDocumentCreator;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

public class Launcher {

    private static final Logger LOG = LoggerFactory.getLogger(Launcher.class);

    private static final File currentFolder = new File(".");

    public static void main(String[] args) throws IOException {
        LOG.info("Starting Template Engine...");

        // scanning HTML files
        File[] htmlFiles = currentFolder.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".htm") || name.endsWith(".html");
            }
        });

        for (File htmlFile : htmlFiles) {
            generateOdt(htmlFile);
        }

        LOG.info("Done");
    }

    private static void generateOdt(File htmlFile) throws IOException {

        // building model
        String html = IOUtils.toString(new FileInputStream(htmlFile), "utf-8");
        HtmlParser parser = new HtmlParser();
        Model model = parser.parseCsddGroup(html);
        Map<String, Object> beans = Maps.newHashMap();
        beans.put("group", model);
        LOG.info("Model from file {} has been successfully parsed", htmlFile.getName());

        // resolving templates folder
        String folderName = htmlFile.getName().replaceAll(".html", "").replaceAll(".htm", "");
        File templatesFolder = new File(htmlFile.getParentFile(), folderName);
        LOG.info("Searching for templates (.odt) in folder {}", templatesFolder);
        Preconditions.checkState(templatesFolder.exists(), String.format("Folder '%s' does not exist in current folder (%s)", templatesFolder, currentFolder));

        // processing every template
        File[] templates = templatesFolder.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".odt") && !name.endsWith("_generated.odt");
            }
        });
        LOG.info("Found templates: " + Arrays.toString(templates));

        for (File templateFile : templates) {
            FileInputStream template = new FileInputStream(templateFile);
            FileOutputStream generated = new FileOutputStream(templateFile.getName() + "_generated.odt");
            new OdtDocumentCreator().create(template, beans, generated);
            LOG.info("File {} completed", templateFile.getName());
        }

    }

}
