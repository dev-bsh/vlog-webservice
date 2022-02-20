import axios from "axios";
// actiontypes

export const FETCH_POST = "FETCH_POST";
export const FETCH_POST_POPULAR = "FETCH_POST_POPULAR";
export const FETCH_POST_DETAIL = "FETCH_POST_DETAIL";
export const TOGGLE_MODAL = "TOGGLE_MODAL";

export const fetchPost = () => async (dispatch) => {
  const response = await axios.get("api/v1/posts/recent");
  dispatch({ type: FETCH_POST, payload: response.data });
};

export const fetchPostPopular = () => async (dispatch) => {
  const response = await axios.get("api/v1/posts/popular");
  dispatch({ type: FETCH_POST_POPULAR, payload: response.data });
};

export const toggle = (isOpened) => {
  return { type: TOGGLE_MODAL, isOpened: isOpened };
};

export const fetchDetailPost = (postId) => async (dispatch) => {
  const response = await axios.get(`/api/v1/posts/${postId}/detail`);
  dispatch({ type: FETCH_POST_DETAIL, payload: response.data });
};

// reducer
const initialState = {
  posts: [],
  isOpened: false,
  postDetail: {},
};

export default function reducer(state = initialState, action) {
  switch (action.type) {
    case FETCH_POST:
      return {
        ...state,
        posts: action.payload,
      };
    case FETCH_POST_POPULAR:
      return {
        ...state,
        posts: action.payload,
      };
    case TOGGLE_MODAL:
      return {
        ...state,
        isOpened: !action.isOpened,
      };
    case FETCH_POST_DETAIL:
      return {
        ...state,
        postDetail: action.payload,
      };
    default:
      return state;
  }
}
