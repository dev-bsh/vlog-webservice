import React from "react";
import RecommandationWrapper from "../styled/commonStyled/RecommandationWrapper";
import RecommandTextWrapper from "../styled/commonStyled/RecommandTextWrapper";
import RecommandationContentStyled from "../styled/commonStyled/RecommandationContentStyled";
import { Link } from "react-router-dom";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import { setInput } from "../../redux/module/search";
import TagWrapper from "../styled/commonStyled/TagWrapper";

const RecommandationTag = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const handleClick = async (e) => {
    const tag = e.target.dataset.tag;

    dispatch(setInput(tag));
    navigate("/search");
  };

  const tags = [1, "jhon", "디진다", "송중기"];
  const tagList = tags.map((tag, index) => (
    <TagWrapper key={index} onClick={handleClick} data-tag={tag}>
      #{tag}
    </TagWrapper>
  ));

  return (
    <RecommandationWrapper>
      <RecommandTextWrapper>Recommnad Tag</RecommandTextWrapper>
      <RecommandationContentStyled>
        <>{tagList}</>
      </RecommandationContentStyled>
    </RecommandationWrapper>
  );
};

export default RecommandationTag;
