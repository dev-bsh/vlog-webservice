import axios from "axios";
import expireToken from "../../utils/expireToken";
import Cookies from "universal-cookie";
const cookies = new Cookies();
// actiontypes;
const FETCH_COMMENTS = "FETCH_COMMENTS";
const SUBMIT_COMMENT = "SUBMIT_COMMENT";
const DELETE_COMMENT = "DELETE_COMMENT";
const EDIT_COMMENT = "EDIT_COMMENT";
const GET_PICTURE = "GET_PICTURE";
const RETYPE_COMMENT = "RETYPE_COMMENT";
// action;

export const fetchComments = (post_id) => async (dispatch) => {
  const response = await axios.get(`/api/v1/posts/${post_id}/comments`);
  dispatch({ type: FETCH_COMMENTS, payload: response.data });
};

export const submitComment = (postsId, userId, input) => async (dispatch) => {
  const data = { postsId: postsId, userId: userId, content: input };
  expireToken();
  const response = await axios.post(
    "http://localhost:8080/api/v2/comments",
    data,
    {
      headers: {
        ACCESS_TOKEN: cookies.get("user").accessToken,
      },
    }
  );
  let lastComment = await axios.get(
    `/api/v1/posts/${postsId}/comments/${response.data + 1}`
  );
  dispatch({ type: SUBMIT_COMMENT, payload: lastComment.data[0] });
};

export const editComment = (postsId, userId, input, commentId) => async (
  dispatch
) => {
  const data = { postsId: postsId, userId: userId, content: input };
  expireToken();
  const response = await axios.put(`api/v2/comments/${commentId}`, data, {
    headers: {
      ACCESS_TOKEN: cookies.get("user").accessToken,
    },
  });

  dispatch({ type: EDIT_COMMENT, payload: input, target: commentId });
};

export const deleteComment = (commentId) => async (dispatch) => {
  expireToken();
  const response = await axios.delete(
    `http://localhost:8080/api/v2/comments/${commentId}`,
    {
      headers: {
        ACCESS_TOKEN: cookies.get("user").accessToken,
      },
    }
  );
  dispatch({ type: DELETE_COMMENT, payload: response.data });
};

export const getPicture = (userId) => async (dispatch) => {
  const response = await axios.get(`api/v1/user/${userId}`);
  let target = response.data;
  dispatch({ type: GET_PICTURE, payload: target });
};

// initialState
const initialState = {
  comments: [],
  picture: {},
  input: "",
  isEdited: false,
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
        comments: [action.payload, ...state.comments],
      };
    case DELETE_COMMENT:
      return {
        ...state,
        comments: state.comments.filter(
          (comment) => comment.commentId !== action.payload
        ),
      };
    case GET_PICTURE:
      return {
        ...state,
        picture: action.payload,
      };
    case EDIT_COMMENT:
      return {
        ...state,
        comments: state.comments,
      };
    default:
      return state;
  }
}
