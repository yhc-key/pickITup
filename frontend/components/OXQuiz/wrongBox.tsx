"use-client";
import { useState } from "react";
import Image from "next/image";

import falseAnswer from "/public/Images/falseAnswer.png";

interface wrongBoxProps {
  user: string;
  answer: string;
}

export default function WrongBox({ user, answer }: wrongBoxProps) {
  return (
    <div className="relative flex justify-center w-48 p-5 text-center align-bottom drop-shadow-md h-28 rounded-3xl bg-f5red-100">
      <Image src={falseAnswer} alt="falseAnswer" />
      <div className="text-lg font-semibold text-center">{answer}</div>
    </div>
  );
}
