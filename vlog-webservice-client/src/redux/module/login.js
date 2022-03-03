import axios from "axios";
import Cookies from "universal-cookie";
import expireToken from "../../utils/expireToken";
const cookies = new Cookies();
//actiontype

const LOGIN_REQUEST = "LOGIN_REQUEST";
const LOGOUT_REQUEST = "LOGOUT_REQUEST";
const GET_CODE = "GET_CODE";
const SEND_CODE = "SEND_CODE";
const GET_PATH = "GET_PATH";
const DELETE_USER = "DELETE_USER";
// action

export const loginRequest = (path, authCode) => async (dispatch) => {
  const response = await axios.get(`http://localhost:8080/api/v1/jwt/${path}`, {
    params: {
      code: authCode,
    },
  });
  cookies.set("user", response.data, { path: "/" });
  cookies.set("isLoggedIn", true, { path: "/" });

  dispatch({ type: LOGIN_REQUEST, payload: response.data });
};

export const logoutRequset = (userId) => async (dispatch) => {
  cookies.remove("isLoggedIn");
  cookies.remove("user");
  dispatch({ type: LOGOUT_REQUEST });
};

export const deleteUser = (userId) => async (dispatch) => {
  expireToken();
  const response = await axios.delete(`api/v2/user/${userId}`, {
    headers: {
      ACCESS_TOKEN: cookies.get("user").accessToken,
    },
  });

  cookies.remove("isLoggedIn");
  cookies.remove("user");
  dispatch({ type: DELETE_USER });
};
export const getCode = (code) => (dispatch) => {
  dispatch({ type: GET_CODE, payload: code });
};

export const getPath = (path) => async (dispatch) => {
  dispatch({ type: GET_PATH, payload: path });
};

// initialState

const initialState = {
  isLoggedIn: false,
  user: {},
  code: null,
  path: "",
};

export default function reducer(state = initialState, action) {
  switch (action.type) {
    case LOGIN_REQUEST:
      return {
        ...state,
        isLoggedIn: true,
        user: action.payload,
      };
    case LOGOUT_REQUEST:
      return {
        ...state,
        isLoggedIn: false,
        user: {},
      };
    case GET_CODE:
      return {
        ...state,
        code: action.payload,
      };
    case GET_PATH:
      return {
        ...state,
        path: action.payload,
      };
    case DELETE_USER:
      return {
        ...state,
        isLoggedIn: false,
        user: {},
      };
    default:
      return state;
  }
}
