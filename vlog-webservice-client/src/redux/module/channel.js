import axios from "axios";

//actiontypes

const FETCH_CHANNEL = "FETCH_CHANNEL";
const FETCH_USER_POST = "USER_FETCH_POST";
const GET_LIST = "GET_LIST";
//action

export const fetchChannel = (userId) => async (dispatch) => {
  const response = await axios.get(
    `http://localhost:8080/api/v1/user/${userId}`
  );
  dispatch({ type: FETCH_CHANNEL, payload: response.data });
};

export const fetchUserPost = (userId) => async (dispatch) => {
  const response = await axios.get(
    `http://localhost:8080/api/v1/user/${userId}/posts`
  );

  dispatch({ type: FETCH_USER_POST, payload: response.data });
};

export const getList = (target) => async (dispatch) => {
  dispatch({ type: GET_LIST, payload: target });
};
//initialState
const initialState = {
  channelData: {},
  userPost: [],
  target: "",
};

//reducer

export default function reducer(state = initialState, action) {
  switch (action.type) {
    case FETCH_CHANNEL:
      return {
        ...state,
        channelData: action.payload,
      };
    case FETCH_USER_POST:
      return {
        ...state,
        userPost: action.payload,
      };
    case GET_LIST:
      return {
        ...state,
        target: action.payload,
      };
    default:
      return state;
  }
}
