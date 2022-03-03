import React, { useState, useEffect } from "react";
import RecommandationWrapper from "../styled/commonStyled/RecommandationWrapper";
import RecommandTextWrapper from "../styled/commonStyled/RecommandTextWrapper";
import RecommandationContentStyled from "../styled/commonStyled/RecommandationContentStyled";
import { Link } from "react-router-dom";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import { setInput } from "../../redux/module/search";
import TagWrapper from "../styled/commonStyled/TagWrapper";
import axios from "axios";
const RecommandationTag = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const [recommendTags, setRecommendTags] = useState([]);

  useEffect(async () => {
    const response = await axios.get(
      `http://localhost:8080/api/v1/recommend/tag`
    );
    setRecommendTags(response.data);
  }, []);
  const handleClick = async (e) => {
    const tag = e.target.dataset.tag.slice(1);

    dispatch(setInput(tag));
    navigate("/search");
  };

  const tagList = recommendTags.map((tag, index) => (
    <TagWrapper key={index} onClick={handleClick} data-tag={tag.content}>
      {tag.content}
    </TagWrapper>
  ));

  return (
    <RecommandationWrapper>
      <RecommandTextWrapper>Recommnad Tag</RecommandTextWrapper>
      <RecommandationContentStyled>{tagList}</RecommandationContentStyled>
    </RecommandationWrapper>
  );
};

export default RecommandationTag;
