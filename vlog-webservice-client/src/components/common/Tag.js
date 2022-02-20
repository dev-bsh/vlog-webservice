import React, { useState } from "react";
import TagWrapper from "../styled/commonStyled/TagWrapper";
import { useSelector, useDispatch } from "react-redux";
import { setInput, getSearch } from "../../redux/module/search";
import { useNavigate } from "react-router-dom";

const Tag = ({ tag, children }) => {
  const posts = useSelector((state) => state.post.postDetail);
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const tags = useSelector((state) => state.search.input);

  const handleClick = async (e) => {
    const keyword = e.target.dataset.tag.slice(1);
    dispatch(setInput(keyword));
    navigate("/search");
  };
  return (
    <div>
      <TagWrapper onClick={handleClick} data-tag={children}>
        {children}
      </TagWrapper>
    </div>
  );
};

export default Tag;
