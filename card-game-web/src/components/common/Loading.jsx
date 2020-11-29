import React from "react";
import ExternalLoading from "react-loading-components";
import styled from "styled-components";
import * as c from "../colors";
import FadeIn from "./FadeIn";

const Wrapper = styled.div`
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  padding-bottom: 5%;
`;

const Loading = () => (
  <FadeIn>
    <Wrapper>
      <ExternalLoading
        type="puff"
        width={100}
        height={100}
        fill={c.lightGrey}
      />
    </Wrapper>
  </FadeIn>
);

export default Loading;
