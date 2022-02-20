import { combineReducers } from "redux";
import postReducer from "./post";
import searchReducer from "./search";
import commentReducer from "./comment";
import modalReducer from "./modal";
import loginReducer from "./login";

export default combineReducers({
  post: postReducer,
  search: searchReducer,
  comment: commentReducer,
  login: loginReducer,
  modal: modalReducer,
});
