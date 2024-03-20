"use-client";

import Image from "next/image";

import falseAnswer from "/public/Images/falseAnswer.png";

export default function WrongBox() {
  return (
    <div className="relative flex justify-center w-48 p-5 text-center align-bottom drop-shadow-md h-28 rounded-3xl bg-f5red-100">
      <Image src={falseAnswer} alt="falseAnswer" />
    </div>
  );
}
