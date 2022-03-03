import { combineReducers } from "redux";
import postReducer from "./post";
import searchReducer from "./search";
import commentReducer from "./comment";
import modalReducer from "./modal";
import loginReducer from "./login";
import likeReducer from "./like";
import channelReducer from "./channel";
import followReducer from "./follow";
import recommendReducer from "./recommend";

export default combineReducers({
  post: postReducer,
  search: searchReducer,
  comment: commentReducer,
  login: loginReducer,
  modal: modalReducer,
  like: likeReducer,
  channel: channelReducer,
  follow: followReducer,
  recommend: recommendReducer,
});
