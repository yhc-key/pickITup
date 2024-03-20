import Image from "next/image";

export default function RightBox() {
  return (
    <div className="relative flex justify-center w-48 p-5 text-center align-bottom drop-shadow-md h-28 rounded-3xl bg-f5blue-100">
      <Image src="/images/trueAnswer.png" alt="trueAnswer" width={70} height={70}/>
    </div>
  );
}
