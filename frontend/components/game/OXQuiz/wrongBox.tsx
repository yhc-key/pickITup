"use client";

import Image from "next/image";


export default function WrongBox() {
  return (
    <div className="relative flex justify-center w-52 p-5 text-center align-bottom drop-shadow-md h-28 rounded-3xl mb:w-36 mb:h-28 bg-f5red-100">
      <Image src="/images/falseAnswer.png" alt="falseAnswer" width={66} height={66} />
    </div>
  );
}
