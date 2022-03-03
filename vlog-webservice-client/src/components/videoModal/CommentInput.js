import React, { useState } from "react";
import InputStyled from "../styled/modalStyled/InputStyled";
import { useSelector, useDispatch } from "react-redux";
import Cookies from "universal-cookie";

import { submitComment } from "../../redux/module/comment";

const CommentInput = () => {
  const cookies = new Cookies();
  const isLoggedIn = cookies.get("isLoggedIn");
  const { postsId } = useSelector((state) => state.post.postDetail);
  const [commentInput, setCommentInput] = useState("");

  const dispatch = useDispatch();

  const handleInput = (e) => {
    setCommentInput(e.target.value);
  };

  const handleClick = () => {
    if (!isLoggedIn) {
      alert("로그인 후 이용가능 합니다.");
      return false;
    }
  };

  const handleSubmit = (e) => {
    if (commentInput !== "" && e.key === "Enter") {
      const userId = cookies.get("user").userId;
      dispatch(submitComment(postsId, userId, commentInput));
      setCommentInput("");
      e.target.value = "";
    }
  };
  return (
    <InputStyled
      placeholder="comment..."
      onClick={handleClick}
      onKeyPress={handleSubmit}
      onChange={handleInput}
    ></InputStyled>
  );
};

export default CommentInput;
