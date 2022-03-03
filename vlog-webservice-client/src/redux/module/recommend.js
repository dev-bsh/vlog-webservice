import axios from "axios";

// actiontypes
const FETCH_RECOMMEND_USERS = "FETCH_RECOMMEND_USERS";
const FETCH_RECOMMEND_TAGS = "FETCH_RECOMMEND_TAGS";

// action

export const fetchRecommendUsers = () => async (dispatch) => {
  const response = await axios.get(
    `http://localhost:8080/api/v1/recommend/user`
  );
  console.log(response);
  dispatch({ type: FETCH_RECOMMEND_USERS, payload: response.data });
};

const initialState = {
  recommendUsers: [],
  recommendTags: [],
};

export default function reducer(state = initialState, action) {
  switch (action.type) {
    case FETCH_RECOMMEND_USERS:
      return {
        ...state,
        recommendUser: action.payload,
      };
    default:
      return state;
  }
}
