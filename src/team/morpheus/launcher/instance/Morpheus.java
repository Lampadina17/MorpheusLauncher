package team.morpheus.launcher.instance;

import com.google.gson.Gson;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import team.morpheus.launcher.Launcher;
import team.morpheus.launcher.Main;
import team.morpheus.launcher.logging.MyLogger;
import team.morpheus.launcher.model.LauncherVariables;
import team.morpheus.launcher.model.MorpheusSession;
import team.morpheus.launcher.model.MorpheusUser;
import team.morpheus.launcher.model.products.MorpheusProduct;
import team.morpheus.launcher.utils.OSUtils;
import team.morpheus.launcher.utils.Utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class Morpheus {

    private static final MyLogger log = new MyLogger(Morpheus.class);
    public MorpheusSession session;
    public JSONParser jsonParser = new JSONParser();

    public MorpheusUser user;

    public Morpheus(MorpheusSession session) {
        this.session = session;
    }

    public void prepareLaunch(String gamePath) throws Exception {
        /* Ask server user json */
        HashMap<String, String> map = new HashMap<>();
        map.put("accessToken", session.getSessionToken());
        HttpURLConnection userInfo = Utils.makePostRequest(new URL(String.format("%s/api/userinfo", Main.getMorpheusAPI())), map);
        map.clear();

        String userjson = Utils.readJsonFromConnection(userInfo);
        if (userInfo.getResponseCode() >= 300) {
            log.error(getErrorMessage(userInfo, userjson));
            return;
        }

        /* Parse user json and make an instance */
        user = new Gson().fromJson(userjson, MorpheusUser.class);

        log.info(String.format("Authenticated as: %s", user.data.username));

        if (!user.data.products.contains(session.getProductID())) {
            log.error("This user doesn't own this product!");
            return;
        }

        if (user.data.hwid == null || session.getHwid() == null) {
            log.debug(String.format("%s %s", user.data.hwid, session.getHwid()));
            return;
        }

        if (!user.data.hwid.equals(session.getHwid())) {
            log.error("This machine currently isn't allowed execute this product! (use hwid reset button)");
            return;
        }

        /* get product details */
        map.put("accessToken", session.getSessionToken());
        map.put("productID", session.getProductID());
        HttpURLConnection appInfo = Utils.makePostRequest(new URL(String.format("%s/api/appinfo", Main.getMorpheusAPI())), map);
        map.clear();

        String prodjson = Utils.readJsonFromConnection(appInfo);
        if (appInfo.getResponseCode() >= 300) {
            log.error(getErrorMessage(appInfo, prodjson));
            return;
        }

        /* parse product json and make an instance */
        MorpheusProduct prod = new Gson().fromJson(prodjson, MorpheusProduct.class);

        /* Invoke launcher class with client instance */
        log.info(String.format("Launching custom instance (%s %s) based on %s", prod.data.name.replace(".jar", ""), prod.data.version, prod.data.gameversion));
        new Launcher(new LauncherVariables(prod.data.gameversion, false, false, gamePath, false), prod);
    }

    public String getErrorMessage(HttpURLConnection conn, String json) throws IOException, ParseException {
        return String.format("%s %s", conn.getResponseCode(), ((JSONObject) (jsonParser.parse(json))).get("message"));
    }
}
