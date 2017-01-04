package de.othr.sw.sharkz.util;

import de.othr.sw.sharkz.entity.Insertion;
import de.othr.sw.sharkz.model.InsertionPublishmentModel;
import de.othr.sw.sharkz.service.InsertionService;
import java.io.IOException;
import java.io.OutputStream;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicException;
import net.sf.jmimemagic.MagicMatch;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import net.sf.jmimemagic.MagicParseException;

@WebServlet("/images")
public class ImageServlet extends HttpServlet {

    @Inject
    InsertionService insertionService;
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        Long insertion_id = Long.parseLong(request.getParameter("insertion_id"));
        int image_id = Integer.parseInt(request.getParameter("image_id"));
        
        Insertion ins = insertionService.getInsertion(insertion_id);
        
        byte[] bytes = ins.getImages().get(image_id);
        
        String mimeType;
        
        try {
            MagicMatch match = Magic.getMagicMatch(bytes);
            mimeType = match.getMimeType();
        } catch (MagicException | MagicMatchNotFoundException
                | MagicParseException ex) {
            mimeType = "image/png";
        }
        
        response.setContentType(mimeType);
        OutputStream out = response.getOutputStream();
        out.write(bytes);
    }
}
