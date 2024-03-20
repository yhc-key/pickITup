import Image from "next/image";

interface rightBoxProps {
  answer: string;
}

export default function RightBox({ answer }: rightBoxProps) {
 
  return (
    <div className="relative p-5 text-center align-bottom drop-shadow-md w-52 h-28 rounded-3xl bg-f5blue-100">
    <Image src="/images/right.png" alt="right" width={27} height={26}/>
    <div className="text-lg font-semibold text-center ">
    {answer}
    </div>
  </div>
  );
}
