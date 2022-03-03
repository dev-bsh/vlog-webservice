import axios from "axios";
// actiontypes
const GET_SEARCH = "GET_SEARCH";
const SET_INPUT = "SET_INPUT";
const RETURN_NULL = "RETURN_NULL";
const GET_USER_SEARCH = "GET_USER_SEARCH";
// action

export const setInput = (input) => async (dispatch) => {
  await dispatch({ type: SET_INPUT, payload: input });
};

export const getSearch = (keyword) => async (dispatch) => {
  if (keyword.length !== 0) {
    const response = await axios.get(`/api/v1/posts/${keyword}/search/recent`);
    if (response.data.length !== 0) {
      dispatch({
        type: GET_SEARCH,
        payload: response.data,
        isPostEmpty: false,
      });
    } else {
      dispatch({ type: GET_SEARCH, payload: response.data, isPostEmpty: true });
    }
  }
};

export const getUserSearch = (keyword, pageNumber) => async (dispatch) => {
  if (keyword.length !== 0) {
    const response = await axios.get(`/api/v1/user/search/${keyword}/0`);
    if (response.data.length !== 0) {
      dispatch({
        type: GET_USER_SEARCH,
        payload: response.data,
        isPostEmpty: true,
      });
    } else {
      dispatch({
        type: GET_USER_SEARCH,
        payload: response.data,
        isPostEmpty: true,
      });
    }
  }
};
// initialState

const initialState = {
  results: [],
  userResults: [],
  input: "",
  isPostEmpty: true,
  isUserEmpty: true,
};
// reducer
export default function reducer(state = initialState, action) {
  switch (action.type) {
    case GET_SEARCH:
      return {
        ...state,
        results: action.payload,
        isPostEmpty: action.isPostEmpty,
      };
    case GET_USER_SEARCH:
      return {
        ...state,
        userResults: action.payload,
        isUserEmpty: action.isUserEmpty,
      };
    case SET_INPUT:
      return {
        ...state,
        input: action.payload,
      };

    default:
      return state;
  }
}
