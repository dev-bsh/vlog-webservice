import axios from "axios";
import Cookies from "universal-cookie";

const cookies = new Cookies();

const expireToken = async () => {
  const accessToken = cookies.get("user").accessToken;
  const refreshToken = cookies.get("user").refreshToken;
  const config = {
    headers: {
      ACCESS_TOKEN: accessToken,
    },
  };
  const validation = await axios.get(
    "http://localhost:8080/api/v1/jwt/expired",
    config
  ).data;

  if (validation) {
    return;
  } else {
    const response = await axios.get(
      "http://localhost:8080/api/v1/jwt/refresh",
      {
        headers: {
          REFRESH_TOKEN: refreshToken,
        },
      }
    );
    let copied = cookies.get("user");
    copied.refreshToken = response.headers.refresh_token;
    copied.accessToken = response.headers.access_token;
    cookies.set("user", copied, { path: "/" });
  }
};

export default expireToken;
