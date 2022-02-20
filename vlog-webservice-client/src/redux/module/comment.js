import axios from "axios";
// actiontypes;
const FETCH_COMMENTS = "FETCH_COMMENTS";
const SUBMIT_COMMENT = "SUBMIT_COMMENT";
// action;

export const fetchComments = (post_id) => async (dispatch) => {
  const response = await axios.get(`/api/v1/posts/${post_id}/comments`);
  dispatch({ type: FETCH_COMMENTS, payload: response.data });
};

export const submitCommen = (comment) => async (dispatch) => {
  const response = await axios.post("url", comment);
  dispatch({ type: SUBMIT_COMMENT, payload: comment });
};

// initialState
const initialState = {
  comments: [],
};

export default function reducer(state = initialState, action) {
  switch (action.type) {
    case FETCH_COMMENTS:
      return {
        ...state,
        comments: action.payload,
      };
    case SUBMIT_COMMENT:
      return {
        ...state,
        comments: [...state.comments, ...action.payload],
      };
    default:
      return state;
  }
}
