import axios from "axios";
// actiontypes
export const GET_SEARCH = "GET_SEARCH";
export const SET_INPUT = "SET_INPUT";
export const RETURN_NULL = "RETURN_NULL";
// action

export const setInput = (input) => async (dispatch) => {
  await dispatch({ type: SET_INPUT, payload: input });
};

export const getSearch = (keyword) => async (dispatch) => {
  if (keyword.length !== 0) {
    const response = await axios.get(`api/v1/posts/${keyword}/search/recent`);
    if (response.data.length !== 0) {
      dispatch({ type: GET_SEARCH, payload: response.data, isEmpty: false });
    } else {
      dispatch({ type: GET_SEARCH, payload: response.data, isEmpty: true });
    }
  }
};

// initialState

const initialState = {
  results: [],
  input: "",
  isEmpty: true,
};
// reducer
export default function reducer(state = initialState, action) {
  switch (action.type) {
    case GET_SEARCH:
      return {
        ...state,
        results: action.payload,
        isEmpty: action.isEmpty,
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
