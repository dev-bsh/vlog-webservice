import React, { useEffect, useState } from "react";
import SearchBoxStyled from "../styled/commonStyled/SearchBoxStyled";
import SearchStyled from "../styled/commonStyled/SearchStyled";
import { FaSearch } from "react-icons/fa";
import { Route, Routes, Link } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import { setInput } from "../../redux/module/search";
import { useNavigate } from "react-router-dom";

const Search = () => {
  const [userInput, setUserInput] = useState("");
  const input = useSelector((state) => state.search.input);
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const handleKeyword = (e) => {
    setUserInput(e.target.value);
  };

  const handleClick = async (e) => {
    await dispatch(setInput(userInput));
    navigate("/search");
  };

  const handlePress = (e) => {
    if (e.key == "Enter") {
      handleClick();
    }
  };

  return (
    <SearchBoxStyled>
      <SearchStyled
        placeholder="Search"
        onChange={handleKeyword}
        onKeyPress={handlePress}
      ></SearchStyled>
      <FaSearch color="#7f8fa6" onClick={handleClick} />
    </SearchBoxStyled>
  );
};

export default Search;
