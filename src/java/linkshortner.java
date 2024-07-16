import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class linkshortner extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String BASE_URL = "http://short.url/";
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int SHORT_URL_LENGTH = 6;

    private Map<String, String> urlMap = new HashMap<>();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String longURL = request.getParameter("longURL");

        try (PrintWriter out = response.getWriter()) {
            if (longURL != null && !longURL.isEmpty()) {
                String shortURL = shortenURL(longURL);
                urlMap.put(shortURL, longURL);
                out.println("<!DOCTYPE html>");
                out.println("<html><body>");
                out.println("<h1>Shortened URL: <a href=\"" + shortURL + "\">" + shortURL + "</a></h1>");
                out.println("</body></html>");
            } else {
                out.println("<!DOCTYPE html>");
                out.println("<html><body>");
                out.println("<h1>Link Shortener</h1>");
                out.println("<form method=\"post\" action=\"\">");
                out.println("Enter Long URL: <input type=\"text\" name=\"longURL\" required/>");
                out.println("<input type=\"submit\" value=\"Shorten URL\" />");
                out.println("</form>");
                out.println("</body></html>");
            }
        }
    }

    private String shortenURL(String longURL) {
        StringBuilder shortURL = new StringBuilder(BASE_URL);
        Random random = new Random();
        for (int i = 0; i < SHORT_URL_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            shortURL.append(CHARACTERS.charAt(index));
        }
        return shortURL.toString();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Link Shortener Servlet";
    }
}
