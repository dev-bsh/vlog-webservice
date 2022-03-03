import axios from "axios";
import expireToken from "../../utils/expireToken";
import Cookies from "universal-cookie";
const cookies = new Cookies();
// actiontype
const SET_FOLLOW = "SET_FOLLOW";
const GET_FOLLOWING = "GET_FOLLOWING";
const SET_UNFOLLOW = "SET_UNFOLLOW";
// action

export const setFollow = (userId, targetId) => async (dispatch) => {
  let data = {
    userId: userId,
    followTargetId: targetId,
  };
  expireToken();
  const response = await axios.post(`/api/v2/follows`, data, {
    headers: {
      ACCESS_TOKEN: cookies.get("user").accessToken,
    },
  });
  dispatch({ type: SET_FOLLOW });
};

export const getFollowing = (userId) => async (dispatch) => {
  const response = await axios.get(`/api/v1/user/${userId}/following`);

  dispatch({
    type: GET_FOLLOWING,
    payload: response.data,
  });
};

export const setUnfollow = (userId, targetId) => async (dispatch) => {
  expireToken();
  const response = await axios.delete(`/api/v2/follows/${userId}/${targetId}`, {
    headers: {
      ACCESS_TOKEN: cookies.get("user").accessToken,
    },
  });
};
const initialState = {
  isFollow: false,
  followingList: [],
};

export default function reducer(state = initialState, action) {
  switch (state.action) {
    case SET_FOLLOW:
      return {
        ...state,
      };
    case GET_FOLLOWING:
      return {
        ...state,
        followingList: action.payload,
      };
    default:
      return state;
  }
}
