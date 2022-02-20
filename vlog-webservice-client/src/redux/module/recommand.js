// actiontypes
const FETCH_RECOMMAND_USERS = "FETCH_RECOMMAND_USERS";
const FETCH_RECOMMAND_TAGS = "FETCH_RECOMMAND_TAGS";

// action

export const fetchRecommandUsers = () => async (dispatch) => {
  const response = await axios.get("");
  dispatch({ type: FETCH_RECOMMAND_USERS, payload: response.data });
};
