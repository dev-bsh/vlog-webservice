import axios from "axios";
import expireToken from "../../utils/expireToken";
import Cookies from "universal-cookie";
const cookies = new Cookies();
// actiontypes

export const FETCH_POST = "FETCH_POST";
export const FETCH_ERROR = "FETCH_ERROR";
export const FETCH_POST_POPULAR = "FETCH_POST_POPULAR";
export const FETCH_POST_DETAIL = "FETCH_POST_DETAIL";
export const TOGGLE_MODAL = "TOGGLE_MODAL";
export const DELETE_POST = "DELETE_POST";
export const EDIT_POST = "EDIT_POST";

export const fetchPost = () => async (dispatch) => {
  const response = await axios.get("api/v1/posts/recent");
  dispatch({ type: FETCH_POST, payload: response.data });
};

export const fetchPostPopular = (pageNum) => async (dispatch) => {
  const response = await axios.get(`api/v1/posts/${pageNum}/popular`);
  dispatch({ type: FETCH_POST_POPULAR, payload: response.data });
};

export const toggle = (isOpened) => {
  return { type: TOGGLE_MODAL, isOpened: isOpened };
};

export const fetchDetailPost = (postId) => async (dispatch) => {
  const response = await axios.get(`/api/v1/posts/${postId}/detail`);
  dispatch({ type: FETCH_POST_DETAIL, payload: response.data });
};

export const deletePost = (postId) => async (dispatch) => {
  expireToken();
  const response = await axios.delete(
    `http://localhost:8080/api/v2/posts/${postId}`,
    {
      headers: {
        ACCESS_TOKEN: cookies.get("user").accessToken,
      },
    }
  );
  dispatch({ type: DELETE_POST });
};

export const editPost = (postId) => async (dispatch) => {};

// reducer
const initialState = {
  posts: [],
  isOpened: false,
  postDetail: {},
  edit: {},
};

export default function reducer(state = initialState, action) {
  switch (action.type) {
    case FETCH_POST:
      return {
        ...state,
        posts: action.payload,
      };
    case FETCH_ERROR:
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
    case DELETE_POST:
      return {
        ...state,
      };
    default:
      return state;
  }
}
